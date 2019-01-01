package cn.edu.hebtu.software.snowcarsh2.activity.childrenfragment5;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.exoplayer2.ExoPlaybackException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import chuangyuan.ycj.videolibrary.listener.VideoInfoListener;
import chuangyuan.ycj.videolibrary.video.GestureVideoPlayer;
import chuangyuan.ycj.videolibrary.video.ManualPlayer;
import cn.edu.hebtu.software.snowcarsh2.R;
import cn.edu.hebtu.software.snowcarsh2.adapter.childrenfragment5.Video2Adapter;
import cn.edu.hebtu.software.snowcarsh2.bean.Video2;

public class VideodetailActivity extends AppCompatActivity {
    private List<Video2> videoList=new ArrayList<Video2>();
    private Video2Adapter videoAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.childrenfragment5_detail);
        Intent intent=getIntent();

        String LinkUrl=intent.getStringExtra("uri");
        String text=intent.getStringExtra("text");
        String title=intent.getStringExtra("title");
        StringBuilder text2=new StringBuilder();
        text2.append("简介：");
        text2.append(text);


        GestureVideoPlayer exoPlayerManager = new ManualPlayer(this,R.id.exo_play_context_id);

        //自定义你的数据源，后面详细介绍如何自定义数据源类
        //http://120.79.80.250:8080/video/a.MKV
        //exoPlayerManager.setPlayUri("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4");
        exoPlayerManager.setPlayUri(LinkUrl);
        exoPlayerManager.setTitle(title);
        exoPlayerManager.setPosition(1000);
        exoPlayerManager.setVideoInfoListener(new VideoInfoListener() {
            @Override
            public void onPlayStart() {
                //开始播放
            }

            @Override
            public void onLoadingChanged() {
                //加载变化
            }

            @Override
            public void onPlayerError(ExoPlaybackException e) {
                //加载错误
            }

            @Override
            public void onPlayEnd() {
                //播放结束
            }

            @Override
            public void onBack() {
                //返回回调

                finish();

            }
            @Override
            public void onRepeatModeChanged(int repeatMode) {
                //模式变化
            }
        });
        TextView view=findViewById(R.id.text);

        SpannableString spannableString = new SpannableString(text2);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(spannableString);


        recyclerView=findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        MyAsycTask myAsycTask = new MyAsycTask();
        //执行异步任务
        myAsycTask.execute("测试数据");


    }


    public static List<Video2> getVideos(){
        List<Video2> VideosList = new ArrayList<>();
        try {

            URL url = new URL("http://120.79.80.250:8080/mysqltest7/a");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("contentType","UTF-8");
            InputStream is = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String res = reader.readLine();
            JSONArray array = new JSONArray(res);
            for (int i = 0; i < array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                Video2 n = new Video2();
                n.setId(object.getInt("id"));
                n.setTitle(object.getString("title"));
                n.setInfo(object.getString("info"));
                n.setImg(object.getString("img"));
                n.setText(object.getString("text"));
                n.setUri(object.getString("uri"));
                VideosList.add(n);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return VideosList;
    }




    private class MyAsycTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            videoList=getVideos();
            return videoList;
        }
        @Override
        //在执行doInBackground之后调用
        protected void onPostExecute(Object o) {

            videoAdapter=new Video2Adapter(R.layout.videodetail,videoList);
            recyclerView.setAdapter(videoAdapter);
            videoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Video2 v1= videoList.get(position);



                    Intent intent=new Intent(VideodetailActivity.this, VideodetailActivity.class);

                    intent.putExtra("uri",v1.getUri());
                    intent.putExtra("text",v1.getText());
                    intent.putExtra("title",v1.getTitle());
                   startActivity(intent);

                }
            });
            super.onPostExecute(o);

        }
    }

}
