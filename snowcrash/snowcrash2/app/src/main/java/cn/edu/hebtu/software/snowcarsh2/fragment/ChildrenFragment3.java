package cn.edu.hebtu.software.snowcarsh2.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import cn.edu.hebtu.software.snowcarsh2.activity.childrenfragment3.ReaddetailActivity;
import cn.edu.hebtu.software.snowcarsh2.adapter.childrenfragment3.ReadAdapter;
import cn.edu.hebtu.software.snowcarsh2.bean.DataRead;

public class ChildrenFragment3 extends Fragment {
    private static final int NUMBER = 1;
    private static final int RESULT = 0;
    public static ChildrenFragment3 newInstance() {
        ChildrenFragment3 fragment = new ChildrenFragment3();
        return fragment;

    }
    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //处理Message,运行在主线程，可以修改界面元素
            switch (msg.what) {
                case RESULT:
                    readList.clear();
                    readList.addAll((List<DataRead>)msg.obj);
                    readAdapter.notifyDataSetChanged();
                    swipeRefresh.setRefreshing(false);;
                    break;
                case NUMBER:
                    if(fromIndex>80){
                        readAdapter.loadMoreEnd();
                    }
                    readList.addAll((List<DataRead>)msg.obj);
                    readAdapter.loadMoreComplete();
            }

        }
    };







    @Nullable
    private List<DataRead> readList=new ArrayList<DataRead>();
    private ReadAdapter readAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private boolean mIsRefreshing=false;
    int fromIndex=0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.childrenfragment3, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
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


        swipeRefresh=(SwipeRefreshLayout)view.findViewById(R.id.swipe);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fromIndex+=20;
                //refreshFruits();
                refreshReads();
            }
        });

        return view;
    }

    public static List<DataRead> getReads(int fromIndex){
        List<DataRead> ReadsList = new ArrayList<>();
        try {
            StringBuilder r =new StringBuilder();
            r.append("http://120.79.80.250:8080/mysqltest6/a?fromIndex=");
            r.append(fromIndex+"&count=10");
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
                DataRead n = new DataRead();
                n.setId(object.getInt("id"));
                n.setTitle(object.getString("title"));
                n.setInfo(object.getString("info"));
                n.setImg(object.getString("img"));
                n.setDate(object.getString("date"));
                n.setUri(object.getString("uri"));
                ReadsList.add(n);


            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ReadsList;
    }

    private class MyAsycTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            readList=getReads(fromIndex);

            return readList;
        }
        @Override
        //在执行doInBackground之后调用
        protected void onPostExecute(Object o) {
            readAdapter=new ReadAdapter(R.layout.childrenfragment3_item,readList);
            recyclerView.setAdapter(readAdapter);
            readAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    fromIndex+=20;
                    loadMoreReads();
                }
            },recyclerView);
            readAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    DataRead d1= readList.get(position);

                    Context context = getActivity();

                    Intent intent=new Intent(context,ReaddetailActivity.class);

                    intent.putExtra("uri",d1.getUri());

                    context.startActivity(intent);

                }
            });
            super.onPostExecute(o);


        }
    }

    private void refreshReads(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    List<DataRead> list2=getReads(fromIndex);
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

    private void loadMoreReads(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    List<DataRead> list3=getReads(fromIndex);
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
