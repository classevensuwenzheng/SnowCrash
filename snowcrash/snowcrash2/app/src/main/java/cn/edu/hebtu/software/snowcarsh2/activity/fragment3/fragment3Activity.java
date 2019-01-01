package cn.edu.hebtu.software.snowcarsh2.activity.fragment3;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.snowcarsh2.R;
import cn.edu.hebtu.software.snowcarsh2.bean.Search;

public class fragment3Activity extends AppCompatActivity {

    int page = 1;
    private String data;
    private SearchAdapter searchAdapter;
    private List<Search> searches;
    private List<Search> searcheAdds;

    private RecyclerView searchView;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        Intent intent=getIntent();
        data = intent.getStringExtra("data");
        Log.e(TAG, "onCreate: "+data );
        searches = new ArrayList<>();

        searchView=(RecyclerView)findViewById(R.id.search_view);

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchView.setLayoutManager(layoutManager);

        SearchThread searchThread = new SearchThread();
        new Thread(searchThread).start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {

                    //创建适配器
                    searchAdapter= new SearchAdapter(R.layout.search_adapter, searches);
                    //给RecyclerView设置适配器
                    searchView.setAdapter(searchAdapter);
                    //添加动画
                    searchAdapter.openLoadAnimation();
                    //上拉事件
                    searchAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            loadMore();
                        }
                    });
                    //点击事件
                    searchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(searches.get(position).getNewUrl()));
                            startActivity(intent);
                        }
                    });


                } else if (msg.what == 2){
                    //更新数据
                    Log.e(TAG, "handleMessage: " );
                    searchAdapter.addData(searcheAdds);
                    searchAdapter.loadMoreComplete();

                }
            }
        };
    }

    private void loadMore() {
        new Thread(new SearchAddThread()).start();
    }

    class SearchThread implements Runnable{

        @Override
        public void run() {
            Log.e(TAG, "线程开始" );
            try {
                Log.e(TAG, "开始抓取" );

                //抓取内容
                Document doc = Jsoup.connect("https://www.gcores.com/search?keyword="+data.toString()+"&page="+page).get();
                page++;
                Elements title = doc.select("div.media_body");

                Search search;
                for(int j = 0;j < title.size();j++){
                    search = new Search();
                    //banner链接(OK)
                    String url = title.get(j).select("a").attr("href");
                    search.setNewUrl(url);
                    //banner图片(OK)
                    String title2 = title.get(j).select("a").text();
                    search.setNewsTitle(title2);
                    String info2 = title.get(j).select("p").text();
                    search.setDesc(info2);
                    searches.add(search);
                }


                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"联网失败",Toast.LENGTH_LONG).show();
                Log.e(TAG, "无法连接" );
            }
        }
    }
    class SearchAddThread implements Runnable{

        @Override
        public void run() {
            searcheAdds = new ArrayList<>();
            Log.e(TAG, "线程开始" );
            try {
                Log.e(TAG, "开始抓取" );

                //抓取内容
                Document doc = Jsoup.connect("https://www.gcores.com/search?keyword="+data.toString()+"&page="+page).get();
                Elements title = doc.select("div.media_body");

                Search search;
                for(int j = 0;j < title.size();j++){
                    search = new Search();
                    //banner链接(OK)
                    String url = title.get(j).select("a").attr("href");
                    search.setNewUrl(url);
                    //banner图片(OK)
                    String title2 = title.get(j).select("a").text();
                    search.setNewsTitle(title2);
                    String info2 = title.get(j).select("p").text();
                    search.setDesc(info2);
                    searcheAdds.add(search);
                }


                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"联网失败",Toast.LENGTH_LONG).show();
                Log.e(TAG, "无法连接" );
            }
        }
    }
    private static final String TAG = "SearchActivity";
}
