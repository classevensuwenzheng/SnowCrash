
package cn.edu.hebtu.software.snowcarsh2.radio.service;

import android.app.SearchManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;


import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;

import cn.edu.hebtu.software.snowcarsh2.radio.App;
import cn.edu.hebtu.software.snowcarsh2.radio.RadiodetailActivity;

public class MediaService extends Service implements CacheListener{


    private static final String TAG = "MediaService";

    private static String proxyurls[]=new String[3];

    //歌曲路径
    private String[] musicPath = new String[]

            {
            "https://alioss.gcores.com/uploads/audio/5ba5cf80-381b-45cf-b387-55560b779da3.mp3\n",
            "https://alioss.gcores.com/uploads/audio/5ba5cf80-381b-45cf-b387-55560b779da3.mp3\n",
            "https://alioss.gcores.com/uploads/audio/5ba5cf80-381b-45cf-b387-55560b779da3.mp3\n"
//            Environment.getExternalStorageDirectory()+"/Sounds/张杰,张碧晨 - 只要平凡.mp3",
//            Environment.getExternalStorageDirectory()+"/Sounds/鞠婧祎 - 叹云兮.mp3",
//            Environment.getExternalStorageDirectory()+"/Sounds/Joel Adams - Please Don't Go.mp3"

    };
    //标记当前歌曲的序号
    private int i = 0;
    private Context mContext;
    private OnCacheListener onCacheListener;

    private  MyBinder mBinder = new MyBinder();

    public interface OnCacheListener {
        void getCacheProgress(int progress);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
        if(onCacheListener!=null){
            onCacheListener.getCacheProgress(percentsAvailable);
        }

    }


    public class MyBinder extends Binder {
        public MyBinder() {

        }

        //        /**
//         *  获取MediaService.this（方便在ServiceConnection中）
//         *
//         * *//*
//        public MediaService getInstance() {
//            return MediaService.this;
//        }*/
        public boolean playstate(){
            StringBuilder a=new StringBuilder();
            try{
            a.append(mMediaPlayer.isPlaying());
            } catch (IllegalStateException e) {
                onDestroy();

            }
            Log.e("msg",a.toString());
            return mMediaPlayer.isPlaying();

        }
        /**
         * 播放音乐
         */
        public void playMusic() {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            }
            else{
                mMediaPlayer.start();
            }
        }
        /**
         * 暂停播放
         */
        public void pauseMusic() {
            if (mMediaPlayer.isPlaying()) {
                //如果还没开始播放，就开始
                mMediaPlayer.pause();
            }
        }

        /**
         * reset
         */
        public void resetMusic() {
            if (!mMediaPlayer.isPlaying()) {
                //如果还没开始播放，就开始
                mMediaPlayer.reset();
                iniMediaPlayerFile(i);
            }
        }

        /**
         * 关闭播放器
         */
        public void closeMedia() {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
            }
        }

        /**
         * 下一首
         */
        public void nextMusic() {
            if (mMediaPlayer != null&& i >= 0) {
                //切换歌曲reset()很重要很重要很重要，没有会报IllegalStateException
                mMediaPlayer.reset();
                iniMediaPlayerFile((i+1)%3);
                i++;
                playMusic();
            }
        }

        /**
         * 上一首
         */
        public void preciousMusic() {
            if (mMediaPlayer != null && i < 4 && i > 0) {
                mMediaPlayer.reset();
                iniMediaPlayerFile(i - 1);
                if (i == 1) {

                } else {

                    i = i - 1;
                }
                playMusic();
            }
        }

        /**
         * 获取歌曲长度
         **/
        public int getProgress() {
            try{
                return mMediaPlayer.getDuration();

            }catch (IllegalStateException e) {

            }
            return mMediaPlayer.getDuration();


        }

        /**
         * 获取播放位置
         */
        public int getPlayPosition() {
          //  Log.e(TAG,""+ mMediaPlayer.getCurrentPosition());
            return mMediaPlayer.getCurrentPosition();
        }
        /**
         * 播放指定位置
         */
        public void seekToPositon(int msec) {
            mMediaPlayer.seekTo(msec);
        }




    }

    //初始化MediaPlayer
    public static MediaPlayer mMediaPlayer = new MediaPlayer();

    public MediaService() {
    }

    public MediaService(Context mContext) {
        this.mContext = mContext;
        HttpProxyCacheServer proxy= App.getProxy(mContext);
        for(int a=0;a<3;a++){
            proxyurls[a]=proxy.getProxyUrl(musicPath[a]);
        }
//        Log.e(TAG, "设置资源，准备阶段出错"+proxyurls[0]+"/n"+proxyurls[1]+"/n"+proxyurls[2]);
        iniMediaPlayerFile(i);
    }
    /**
     * 检查缓存
     */
//    private  void checkCachedState(){
//        HttpProxyCacheServer proxy = App.getProxy(mContext);
//        boolean fullyCached = proxy.isCached(url);
//        if (fullyCached && onCacheListener != null) {
//            onCacheListener.getCacheProgress(100);
//        }
//    }
    /**
     * 添加file文件到MediaPlayer对象并且准备播放音频
     */
    private void iniMediaPlayerFile(int dex) {
        //获取文件路径
        try {

            //此处的两个方法需要捕获IO异常
            //设置音频文件到MediaPlayer对象中
            mMediaPlayer.setDataSource(proxyurls[dex]);
            //让MediaPlayer对象准备
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                }
            });
        } catch (IOException e) {
            Log.d(TAG, "设置资源，准备阶段出错");
            e.printStackTrace();
        }catch (IllegalStateException e) {

        }
    }


    @Override
    public void onDestroy() {
        Intent stopIntent=new Intent(this,MediaService.class);
        stopService(stopIntent);
        super.onDestroy();
    }
}