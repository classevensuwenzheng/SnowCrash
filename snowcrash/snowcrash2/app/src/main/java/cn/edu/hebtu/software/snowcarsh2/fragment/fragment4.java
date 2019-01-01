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
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import cn.edu.hebtu.software.snowcarsh2.activity.fragment4.ProduceActivity;
import cn.edu.hebtu.software.snowcarsh2.adapter.fragment4.fragment4Adapter;
import cn.edu.hebtu.software.snowcarsh2.bean.content;

public class fragment4 extends Fragment {
    private List<content> contentList=new ArrayList<>();
    private RecyclerView mRecyclerView;
    private fragment4Adapter mMyAdapter;
    private SwipeRefreshLayout swipeRefresh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment4,container,false);


        mRecyclerView=view.findViewById(R.id.rv_list);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);

        MyAsycTask myAsycTask = new MyAsycTask();
        //执行异步任务
        myAsycTask.execute("测试数据");

        swipeRefresh=(SwipeRefreshLayout)view.findViewById(R.id.swipeLayout);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(),"没有更多了",Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);

            }
        });





        return view ;
    }
    public static List<content> getProduces(){
        List<content> List = new ArrayList<>();
        try {
            StringBuilder r =new StringBuilder();
            URL url = new URL("http://120.79.80.250:8080/mysqltext5/b");
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
                content n = new content();
                n.setId(object.getInt("id"));
                n.setImg(object.getString("img"));
                n.setInfo(object.getString("info"));
                n.setPrice(object.getString("price"));
                n.setUri(object.getString("uri"));
                List.add(n);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return List;
    }
    private class MyAsycTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            contentList=getProduces();
            return  contentList;
        }
        @Override
        //在执行doInBackground之后调用
        protected void onPostExecute(Object o) {
            mMyAdapter=new fragment4Adapter(R.layout.fragment4_item,contentList);
            mMyAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            View view2= LayoutInflater.from(getContext()).inflate(R.layout.fragment4_header,mRecyclerView, false);
            ImageView imageView=view2.findViewById(R.id.img);
            Glide.with(getContext()).load("http://img.alicdn.com/bao/uploaded/i2/2549490648/O1CN01LUR2BZ1Gep7xtAxrb_!!2549490648.jpg_180x180.jpg").into(imageView);
            mMyAdapter.addHeaderView(view2);
            mRecyclerView.setAdapter(mMyAdapter);
            mMyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    content c1= contentList.get(position);

                    Context context = getActivity();

                    Intent intent=new Intent(context,ProduceActivity.class);

                    intent.putExtra("uri",c1.getUri());

                    context.startActivity(intent);

                }
            });
            super.onPostExecute(o);
        }
    }
}
