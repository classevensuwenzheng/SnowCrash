package cn.edu.hebtu.software.snowcarsh2.radio;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cn.edu.hebtu.software.snowcarsh2.R;
import cn.edu.hebtu.software.snowcarsh2.radio.adapter.ViewPagerAdapter;
import cn.edu.hebtu.software.snowcarsh2.radio.adapter.ViewPagerAdapter1;
import cn.edu.hebtu.software.snowcarsh2.radio.bean.Date;
import cn.edu.hebtu.software.snowcarsh2.radio.service.MediaService;



import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RadiodetailActivity extends AppCompatActivity  implements View.OnClickListener{

    private List<String> mpicss;
    private Handler mHandler = new Handler();
    private static final String TAG = "MainActivity";
    private MediaService mediaService=new MediaService();
    private MediaService.MyBinder mMyBinder=mediaService.new MyBinder();
    //private MediaService mediaService;
    private ImageView play;
    private ImageView next;
    private ImageView mfinish;
    private SeekBar mSeekBar;
    private TextView mTextView;
    //进度条下面的当前进度文字，将毫秒化为m:ss格式
    private SimpleDateFormat time = new SimpleDateFormat("m:ss");
    //“绑定”服务的intent
    Intent MediaServiceIntent;
    //横向画廊
    private ViewPager mViewPager;
    private ViewPager mViewPager2;
    private LinearLayout page_layout;
    private LinearLayout lll_layout;
    //数据
    private Date mdate;

    //初始化MediaPlayer
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);

        }
    };
    private Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mViewPager2.setCurrentItem(mViewPager2.getCurrentItem()+1);
        }
    };

    private Handler handler3=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ImageView imageView=findViewById(R.id.mp3_play);
            try{
            if (!mMyBinder.playstate()){
                imageView.setImageResource(R.mipmap.stopmusic);
            }else if (mMyBinder.playstate()){
                imageView.setImageResource(R.mipmap.playmusic);}
            } catch (IllegalStateException e) {
                Intent stopIntent=new Intent(RadiodetailActivity.this,MediaService.class);
                stopService(stopIntent);

            }
        }
    };
    //    private Handler handler4=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            MusicDate musicDate=new MusicDate();
//            musicDate.date();
//        }
//    };
////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.radiodetail);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        iniView();
        iniimg();
        shuService();



    }
    //绑定service服务
    public void shuService(){
        try {
            mediaService= new MediaService(this);
        }catch (IllegalStateException e) {
            Intent stopIntent=new Intent(this,MediaService.class);
            stopService(stopIntent);

        }


        MediaServiceIntent = new Intent(this, MediaService.class);
        //判断权限够不够
        if (ContextCompat.checkSelfPermission(RadiodetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RadiodetailActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        } else {
            //够了就设置路径等，准备播放
            bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        }}
    //获取到权限回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[]permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
                } else {
                    Toast.makeText(this, "权限不够获取不到音乐，程序将退出", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (MediaService.MyBinder) service;
            try{
                mSeekBar.setMax(mMyBinder.getProgress());

            }catch (IllegalStateException e) {


            }


            mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(fromUser){
                        mMyBinder.seekToPositon(seekBar.getProgress());
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });


            mHandler.post(mRunnable);

            Log.d(TAG, "Service与Activity已连接");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };





    private void iniView() {
        mdate=new Date();
        play =  findViewById(R.id.mp3_play);
        next =  findViewById(R.id.mp3_next);
        mfinish=findViewById(R.id.finish_diantai);

        mTextView = (TextView) findViewById(R.id.time_text);
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        mfinish.setOnClickListener(this);
        //轮播图片和文字内容----------------------------
        mViewPager=(ViewPager) findViewById(R.id.viewPager);
        mViewPager2=(ViewPager) findViewById(R.id.viewPager2);
        page_layout=findViewById(R.id.page_layout);
        lll_layout=findViewById(R.id.lll_layout);
        //设置适配器——pageview-------------------------------
//        Log.e(TAG, "iniView: "+mpicss );
        mViewPager.setAdapter(new ViewPagerAdapter(this,mdate.getmPics()));
        mViewPager2.setAdapter(new ViewPagerAdapter1(this,mdate.getMname(),mdate.getMcontext()));
        //切换动作的时间控制----------------------------
        try {
            Field mField1 = ViewPager.class.getDeclaredField("mScroller");
            mField1.setAccessible(true);
            FixedSpeedScroller scroller =new FixedSpeedScroller(mViewPager.getContext(),
                    new AccelerateInterpolator());
            mField1.set(mViewPager,scroller);
            scroller.setmDuration(20000);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Field mField2 = ViewPager.class.getDeclaredField("mScroller");
            mField2.setAccessible(true);
            FixedSpeedScroller scroller =new FixedSpeedScroller(mViewPager2.getContext(),
                    new AccelerateInterpolator());
            mField2.set(mViewPager2,scroller);
            scroller.setmDuration(20000);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {

            e.printStackTrace();
        }
        //-----------------------------------------------
        mViewPager.setPageMargin(20);
        mViewPager2.setPageMargin(20);
        mViewPager.setOffscreenPageLimit(mdate.getmPicsLength());
        mViewPager2.setOffscreenPageLimit(mdate.getMnameLength());
        mViewPager.setPageTransformer(true,new ZoomOutPageTransformer());
        mViewPager2.setPageTransformer(true,new ZoomOutPageTransformer());
        //左右都有图片
        mViewPager.setCurrentItem(1);
        mViewPager2.setCurrentItem(0);
        //无限循环
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentPosition;
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPosition=i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                // ViewPager.SCROLL_STATE_IDLE 标识的状态是当前页面完全展现，
                // 并且没有动画正在进行中，如果不
                // 是此状态下执行 setCurrentItem 方法回在首位替换的时候会出现跳动！
                if (i!=ViewPager.SCROLL_STATE_IDLE)return;
                //当视图在第一个时，将页号设置为图片的最后一张。
                if (currentPosition==0){
                    mViewPager.setCurrentItem(mdate.getmPicsLength()-2,false);
                    mViewPager2.setCurrentItem(mdate.getMnameLength()-2,false);
                }else if (currentPosition==mdate.getmPicsLength()-1){
                    mViewPager.setCurrentItem(1,false);
                    mViewPager2.setCurrentItem(1,false);
                }

            }
        });
        mViewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentPosition;
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPosition=i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                // ViewPager.SCROLL_STATE_IDLE 标识的状态是当前页面完全展现，
                // 并且没有动画正在进行中，如果不
                // 是此状态下执行 setCurrentItem 方法回在首位替换的时候会出现跳动！
                if (i!=ViewPager.SCROLL_STATE_IDLE)return;
                //当视图在第一个时，将页号设置为图片的最后一张。
                if (currentPosition==0){
                    mViewPager.setCurrentItem(mdate.getmPicsLength()-2,false);
                }else if (currentPosition==mdate.getmPicsLength()-1){
                    mViewPager.setCurrentItem(1,false);
                }

            }
        });
        //VIEWPAGER左右滑动无效的处理
        page_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });
        lll_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager2.dispatchTouchEvent(event);
            }
        });
        //图片切换控制---------
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(true){
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(2);
                }
            }
        }).start();
        //文字切换控制----------------
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler2.sendEmptyMessage(2);
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler3.sendEmptyMessage(2);
                }
            }
        }).start();
    }

    private void iniimg(){
        ImageView imageView=findViewById(R.id.poster);
        String url="https://image.gcores.com/69da7c3b-2d62-4f7d-a58a-4fb40f3fd61a.jpg?x-oss-process=style/original_hl";

        Glide.with(RadiodetailActivity.this).load(url).into(imageView);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mp3_play:
                ImageView imageView=findViewById(R.id.mp3_play);
                if(mMyBinder.playstate()){
                    Log.e("","暂停");
                    imageView.setImageResource(R.mipmap.stopmusic);
                }else{
                    Log.e("","开始");
                    imageView.setImageResource(R.mipmap.playmusic);}

                mMyBinder.playMusic();
                break;
            case R.id.mp3_next:
                mMyBinder.nextMusic();
                break;

            case R.id.finish_diantai:
                Intent stopIntent=new Intent(this,MediaService.class);
                stopService(stopIntent);

                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //我们的handler发送是定时1000s发送的，如果不关闭，MediaPlayer release掉了还在获取getCurrentPosition就会爆IllegalStateException错误
        mHandler.removeCallbacks(mRunnable);
        mMyBinder.closeMedia();
        unbindService(mServiceConnection);
        Intent stopIntent=new Intent(this,MediaService.class);
        stopService(stopIntent);
    }

    /**
     * 更新ui的runnable
     */
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mSeekBar.setProgress(mMyBinder.getPlayPosition());
            mTextView.setText(time.format(mMyBinder.getPlayPosition()) + "s");
            mHandler.postDelayed(mRunnable, 1000);
        }
    };

}

