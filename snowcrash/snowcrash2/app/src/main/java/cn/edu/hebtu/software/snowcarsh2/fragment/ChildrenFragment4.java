package cn.edu.hebtu.software.snowcarsh2.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import cn.edu.hebtu.software.snowcarsh2.adapter.childrenfragment4.RadioAdapter;
import cn.edu.hebtu.software.snowcarsh2.bean.Radio2;
import cn.edu.hebtu.software.snowcarsh2.radio.RadiodetailActivity;

public class ChildrenFragment4 extends Fragment{
    private static final int NUMBER = 1;
    private static final int RESULT = 0;
    private  RecyclerView recyclerView;
    private RadioAdapter adapter2;
    int fromIndex2=0;
    public static Fragment newInstance() {
        ChildrenFragment4 fragment = new ChildrenFragment4();
        return fragment;


    }
    private  List<Radio2> List1=new ArrayList<>();
    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //处理Message,运行在主线程，可以修改界面元素
            switch (msg.what) {
                case RESULT:
                    List1.clear();
                    List1.addAll((List<Radio2>)msg.obj);
                    adapter2.notifyDataSetChanged();
                    swipeRefresh.setRefreshing(false);
                    break;
                case NUMBER:
                    if(fromIndex2>80){
                        adapter2.loadMoreEnd();
                    }
                    List1.addAll((List<Radio2>)msg.obj);
                    adapter2.loadMoreComplete();
                    break;
            }

        }
    };











    private SwipeRefreshLayout swipeRefresh;
    private boolean mIsRefreshing=false;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.childrenfragment4,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        MyAsycTask myAsycTask = new MyAsycTask();
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

        swipeRefresh=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fromIndex2+=20;
                //refreshFruits();
                refreshRadios();

            }
        });

        return view ;

    }

    public static List<Radio2> getRadios(int fromIndex2){
        List<Radio2> radioList = new ArrayList<>();
        try {
            StringBuilder r =new StringBuilder();
            r.append("http://120.79.80.250:8080/mysqltest4/RadiosServlet?fromIndex=");
            r.append(fromIndex2+"&count=20");

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
                Radio2 n = new Radio2();
                n.setId(object.getInt("id"));
                n.setTitle(object.getString("title"));
                n.setInfo(object.getString("info"));
                n.setImg(object.getString("img"));
                n.setDate(object.getString("date"));
                n.setUri(object.getString("uri"));
                radioList.add(n);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return radioList;
    }


    private class MyAsycTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            List1=getRadios(fromIndex2);
            return List1;
        }
        @Override
        //在执行doInBackground之后调用
        protected void onPostExecute(Object o) {

            adapter2=new RadioAdapter(R.layout.childrenfragment4_item,List1);
            recyclerView.setAdapter(adapter2);
            adapter2.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    fromIndex2+=20;
                    loadMoreRadios();
                }
            },recyclerView);
            adapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent=new Intent(getContext(),RadiodetailActivity.class);
                    startActivity(intent);
                }
            });

            super.onPostExecute(o);
        }



    }

    private void refreshRadios(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    List<Radio2> list2=getRadios(fromIndex2);
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

    private void loadMoreRadios(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(2000);
                    List<Radio2> list3=getRadios(fromIndex2);
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
