package cn.edu.hebtu.software.snowcarsh2.activity.startActvity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.snowcarsh2.MainActivity;
import cn.edu.hebtu.software.snowcarsh2.R;
import cn.edu.hebtu.software.snowcarsh2.activity.adapter.ViewPagerAdapter;

public class WelcomeActivity extends AppCompatActivity implements
        ViewPager.OnPageChangeListener{
    private ViewPager vP;
    private int []imageArray;
    private ViewGroup viewGroup;
    private List<View> viewsList;
    private ImageView iv_point;
    private ImageView [] iv_PointArray;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        button = (Button)findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener(){

            @Override//设置监听，当滑动结束后点击按钮跳转到App主页面
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            }
        });
        //初始化要加载的页面，即滑动页面
        initViewPager();
        //初始化活动要加载的点，即滑动时的小圆点
        initPoint();
    }
    private void initViewPager(){
        //加载第一张启动页面
        vP = (ViewPager)findViewById(R.id.viewpager_launcher);
        //滑动页面放到一个imageArray数组中
        imageArray = new int[]{R.drawable.sl,R.drawable.j2,R.drawable.welcome};
        viewsList = new ArrayList<>();
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
        //获取imageArray数组的长度，现实当前页面，当数组中还有页面时，继续添加到viewList中显示
        int len = imageArray.length;
        for (int i = 0;i<len;i++){
            ImageView IV = new ImageView(this);
            IV.setLayoutParams(params);
            IV.setBackgroundResource(imageArray[i]);
            viewsList.add(IV);
        }
        //实例化ViewPagerAapter
        vP.setAdapter(new ViewPagerAdapter(viewsList));
        //设置滑动监听
        vP.setOnPageChangeListener(this);
    }

    private  void  initPoint() {
        viewGroup = (ViewGroup)findViewById(R.id.dot);
        iv_PointArray = new ImageView[viewsList.size()];
        int size = viewsList.size();
        for(int i = 0; i <size;i++){
            iv_point = new ImageView(this);
            //实例化圆点，设置圆点的参数大小，位置
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(60,60);
            lp.leftMargin = 20;
            lp.rightMargin = 20;
            iv_point.setLayoutParams(lp);


            iv_PointArray[i] = iv_point;
            if(i == 0){
                iv_point.setBackgroundResource(R.drawable.pot);
            }else{
                iv_point.setBackgroundResource(R.drawable.blackpot);
            }
            viewGroup.addView(iv_PointArray[i]);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override//滑动页面具体实现方法
    public void onPageSelected(int position) {
        int lenth = imageArray.length;
        for (int i = 0; i < lenth; i++) {
            iv_PointArray[i].setBackgroundResource(R.drawable.pot);
            if (position != i) {
                iv_PointArray[i].setBackgroundResource(R.drawable.blackpot);
            }
            if (position == lenth - 1) {
                button.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
