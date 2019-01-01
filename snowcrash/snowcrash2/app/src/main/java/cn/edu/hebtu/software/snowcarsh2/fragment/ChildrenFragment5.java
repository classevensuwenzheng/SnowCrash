package cn.edu.hebtu.software.snowcarsh2.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.chad.library.adapter.base.BaseQuickAdapter;

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

import cn.edu.hebtu.software.snowcarsh2.R;
import cn.edu.hebtu.software.snowcarsh2.activity.childrenfragment5.VideodetailActivity;
import cn.edu.hebtu.software.snowcarsh2.adapter.childrenfragment5.Video2Adapter;
import cn.edu.hebtu.software.snowcarsh2.bean.Video2;


public class ChildrenFragment5 extends Fragment {
    public static ChildrenFragment5 newInstance() {
        ChildrenFragment5 fragment = new ChildrenFragment5();
        return fragment;

    }
    @Nullable
    private List<Video2> videoList=new ArrayList<Video2>();
    private Video2Adapter videoAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.childrenfragment5, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        MyAsycTask myAsycTask = new MyAsycTask();

        //执行异步任务
        myAsycTask.execute("测试数据");

        return view;
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

            videoAdapter=new Video2Adapter(R.layout.childrenfragment5_item,videoList);
            recyclerView.setAdapter(videoAdapter);
            videoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Video2 v1= videoList.get(position);

                    Context context = getActivity();

                    Intent intent=new Intent(context, VideodetailActivity.class);

                    intent.putExtra("uri",v1.getUri());
                    intent.putExtra("text",v1.getText());
                    intent.putExtra("title",v1.getTitle());

                    context.startActivity(intent);

                }
            });
            super.onPostExecute(o);

        }
    }
}
