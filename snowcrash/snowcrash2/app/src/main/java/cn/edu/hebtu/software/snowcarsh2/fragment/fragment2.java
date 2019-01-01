package cn.edu.hebtu.software.snowcarsh2.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
import cn.edu.hebtu.software.snowcarsh2.adapter.fragment2.HomeAdapter;
import cn.edu.hebtu.software.snowcarsh2.bean.say;

public class fragment2 extends Fragment {
    private List<say> List=new ArrayList<>();
    private RecyclerView mRecyclerView;
    private HomeAdapter mMyAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment2,container,false);
        mRecyclerView=view.findViewById(R.id.rev);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        MyAsycTask myAsycTask = new MyAsycTask();
        //执行异步任务
        myAsycTask.execute("测试数据");
        return view ;
    }





    public static List<say> getSays(){
        List<say> saysList = new ArrayList<>();
        try {
            StringBuilder r =new StringBuilder();
            URL url = new URL("http://120.79.80.250:8080/mysqltext5/a");
            //URL url = new URL(r.toString());
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
                say n = new say();
                n.setIcon(object.getString("icon"));
                n.setName(object.getString("name"));
                n.setDate(object.getString("date"));
                n.setTitle(object.getString("title"));
                n.setImg(object.getString("img"));
                n.setContent(object.getString("content"));
                saysList.add(n);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return saysList;
    }
    private class MyAsycTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            List=getSays();
            return List;
        }
        @Override
        //在执行doInBackground之后调用
        protected void onPostExecute(Object o) {
            mMyAdapter=new HomeAdapter(R.layout.fragment2_item,List);
            View view2= LayoutInflater.from(getContext()).inflate(R.layout.fragment2_header,mRecyclerView, false);
            mMyAdapter.addHeaderView(view2);
            mRecyclerView.setAdapter(mMyAdapter);
            super.onPostExecute(o);
        }
    }
}
