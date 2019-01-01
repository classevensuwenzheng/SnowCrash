package cn.edu.hebtu.software.snowcarsh2.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import cn.edu.hebtu.software.snowcarsh2.activity.childrenfragment2.NewsDetailActivity;
import cn.edu.hebtu.software.snowcarsh2.adapter.childrenfragment2.RecyclerAdapter2;
import cn.edu.hebtu.software.snowcarsh2.bean.IndexHorizontal;



/**
 * Created by zq on 2017/1/12.
 */
public class ChildrenFragment2 extends Fragment {
    private static final int NUMBER = 1;
    private static final int RESULT = 0;
    private RecyclerAdapter2 adapter2;
    private RecyclerView recyclerView;
    int fromIndex=0;




    public static Fragment newInstance() {
        ChildrenFragment2 fragment = new ChildrenFragment2();
        return fragment;

    }

    private  List<IndexHorizontal> List1=new ArrayList<>();
    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //处理Message,运行在主线程，可以修改界面元素
            switch (msg.what) {
                case RESULT:
                    List1.clear();
                    List1.addAll((List<IndexHorizontal>)msg.obj);
                    adapter2.notifyDataSetChanged();
                    swipeRefresh.setRefreshing(false);;
                    break;
                case NUMBER:
                    if(fromIndex>80){
                        adapter2.loadMoreEnd();
                    }
                    List1.addAll((List<IndexHorizontal>)msg.obj);
                    adapter2.loadMoreComplete();
            }

        }
    };







    private SwipeRefreshLayout swipeRefresh;
    private boolean mIsRefreshing=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.childrenfragment2, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        MyAsycTask myAsycTask = new MyAsycTask();
        //执行异步任务
        myAsycTask.execute("测试数据");


        recyclerView.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (mIsRefreshing) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                });





        swipeRefresh=(SwipeRefreshLayout)rootView.findViewById(R.id.swipe);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fromIndex+=20;
                //refreshFruits();
                refreshNews();
            }
        });






        return rootView;

    }















    public static List<IndexHorizontal> getNews(int fromIndex){
        List<IndexHorizontal> newsList = new ArrayList<>();
        try {
            StringBuilder r =new StringBuilder();
            r.append("http://120.79.80.250:8080/mysqltest3/NewsServlet?fromIndex=");
            r.append(fromIndex+"&count=20");


            //URL url = new URL("http://120.79.80.250:8080/mysqltest3/NewsServlet?fromIndex=0&count=20");
            URL url = new URL(r.toString());
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
                IndexHorizontal n = new IndexHorizontal();
                n.setId(object.getInt("id"));
                n.setTitle(object.getString("title"));
                n.setIntroduce(object.getString("info"));
                n.setImgUrl(object.getString("img"));
                n.setTime(object.getString("date"));
                n.setLinkUrl(object.getString("uri"));
                newsList.add(n);


            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsList;
    }




    private class MyAsycTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {

            List1=getNews(fromIndex);


            return List1;
        }
        @Override
        //在执行doInBackground之后调用
        protected void onPostExecute(Object o) {
            adapter2=new RecyclerAdapter2(R.layout.childrenfragment2_item,List1);
            recyclerView.setAdapter(adapter2);
            adapter2.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    fromIndex+=20;
                    loadMoreNews();
                }
            },recyclerView);

            adapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    IndexHorizontal new1= List1.get(position);

                    Context context = getActivity();

                    Intent intent=new Intent(context,NewsDetailActivity.class);

                    intent.putExtra("uri",new1.getLinkUrl());

                    context.startActivity(intent);

                }
            });

            super.onPostExecute(o);
        }



    }
    private void refreshNews(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    List<IndexHorizontal> list2=getNews(fromIndex);
                    Message message = new Message();
                    message.obj=list2;
                    //添加标识
                    message.what = RESULT;
                    mainHandler.sendMessage(message);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();

    }

    private void loadMoreNews(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    List<IndexHorizontal> list3=getNews(fromIndex);
                    Message message = new Message();
                    message.obj=list3;
                    //添加标识
                    message.what = NUMBER;
                    mainHandler.sendMessage(message);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();

    }





}
