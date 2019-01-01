package cn.edu.hebtu.software.snowcarsh2.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.hebtu.software.snowcarsh2.R;
import cn.edu.hebtu.software.snowcarsh2.adapter.childrenfragment1.NewsAdapter;
import cn.edu.hebtu.software.snowcarsh2.adapter.childrenfragment1.NewsHorizontalAdapter;
import cn.edu.hebtu.software.snowcarsh2.bean.Home.New;
import cn.edu.hebtu.software.snowcarsh2.bean.Home.NewsHorizontal;
import cn.edu.hebtu.software.snowcarsh2.tool.GlideImageLoader;


public class ChildrenFragment1 extends Fragment {
    public static ChildrenFragment1 newInstance() {
        ChildrenFragment1 fragment = new ChildrenFragment1();
        return fragment;

    }
    private int loading=0;
    private int page=1;
    private View xinwenbiantiView;
    private View bannerView;
    private RecyclerView recyclerView;
    private View horView;

    //纵向列表
    private List<New> datas;
    private List<New> datasAdd;
    private NewsAdapter adapter;
    //横向列表
    private RecyclerView recyclerView2;
    private List<NewsHorizontal> newsHorList;
    private NewsHorizontalAdapter newsHorizontalAdapter;


    private Handler handler;
    //baner图片地址以及标题
    private Banner mBanner;
    List<String> bannerImages = new ArrayList<>();
    List<String> bannerUrl = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.childrenfragment1, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        bannerView=LayoutInflater.from(getContext()).inflate(R.layout.banner_view, null);

        horView=LayoutInflater.from(getContext()).inflate(R.layout.new_adapter_view, null);
        recyclerView2=(RecyclerView) horView.findViewById(R.id.recycler_horizontal_vertical);

        xinwenbiantiView=LayoutInflater.from(getContext()).inflate(R.layout.xinwenbiaoti_view, null);

        if(loading==0)
        {
            datas = new ArrayList<>();
            newsHorList = new ArrayList<>();
        }

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);



        LinearLayoutManager layoutManager2 = new LinearLayoutManager(recyclerView2.getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(layoutManager2);


        NewsUpdateThread newsUpdateThread = new NewsUpdateThread();
        new Thread(newsUpdateThread).start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {

                    newsHorizontalAdapter = new NewsHorizontalAdapter(R.layout.new_adapter_heng, newsHorList);

                    recyclerView2.setAdapter(newsHorizontalAdapter);
                    //点击事件
                    newsHorizontalAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(newsHorList.get(position).getNewUrl()));
                            startActivity(intent);
                        }
                    });

                    //初始化
                    //创建适配器
                    adapter = new NewsAdapter(R.layout.new_adapter, datas);
                    adapter.addHeaderView(bannerView);
                    adapter.addHeaderView(xinwenbiantiView);
                    adapter.addHeaderView(horView);
                    //给RecyclerView设置适配器
                    recyclerView.setAdapter(adapter);
                    //添加动画
                    adapter.openLoadAnimation();
                    //上拉事件
                    adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            loadMore();
                        }
                    });
                    //点击事件
                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(datas.get(position).getNewUrl()));
                            startActivity(intent);
                        }
                    });

                    setBanner();

                } else if (msg.what == 2){
                    //更新数据
                    Log.e(TAG, "handleMessage: " );
                    adapter.addData(datasAdd);
                    adapter.loadMoreComplete();

                }
            }
        };
        return view;

    }
    private void loadMore() {
        new Thread(new NewsAddThread()).start();
    }


    class NewsUpdateThread implements Runnable{

        @Override
        public void run() {
            Log.e(TAG, "线程开始" );
            try {
                if (loading == 0){
                    Log.e(TAG, "开始抓取");

                    //抓取banner
                    Document docBanner = Jsoup.connect("https://www.gcores.com/").get();
                    //抓取垂直列表
                    Document doc = Jsoup.connect("https://www.gcores.com/categories/1/originals?page=" + page).get();
                    //抓取水平列表
                    Log.e(TAG, "抓取水平");
                    Document docHorizontal = Jsoup.connect("https://www.gcores.com/categories/2/originals?page=1").get();

                    Elements imgBannerLinks = docBanner.select("img.visible-xs-inline-block");
                    Elements imgBannerUrl = docBanner.select("div.swiper-wrapper").select("a");
                    Log.e(TAG, imgBannerUrl.toString());
    //                Elements urlBannerLinks = docBanner.select("div.swiper-slide");
                    for (int j = 0; j < imgBannerLinks.size(); j++) {
                        //banner链接(OK)
                        String imgUrl1 = imgBannerUrl.select("a").attr("href");
                        bannerUrl.add(imgUrl1);
                        //banner图片(OK)
                        String imgUrl = imgBannerLinks.get(j).attr("src");
                        bannerImages.add(imgUrl);
                        Log.e(TAG, "Banner:" + imgUrl);
                    }

                    page++;
                    Elements imgLinks = doc.select("div.showcase_img");
                    Elements textLinks = doc.select("div.showcase_text");
                    New new2;
                    for (int j = 0; j < imgLinks.size(); j++) {
                        new2 = new New();
                        //标题(OK)
                        String title = textLinks.get(j).select("h4").text();
                        new2.setNewsTitle(title);
                        Log.e(TAG, "title:" + title);
                        //简介(ok)
                        String desc = textLinks.get(j).select("div.showcase_info").text();
                        new2.setDesc(desc);
                        Log.e(TAG, "desc:" + desc);
                        //网址(OK)
                        String url = imgLinks.get(j).select("a").attr("href");
                        new2.setNewUrl(url);
                        Log.e(TAG, "url:" + url);
                        //图片(OK)
                        String imgUrl = imgLinks.get(j).select("img").attr("src");
                        new2.setImgUrl(imgUrl);
                        Log.e(TAG, "imgurl:" + imgUrl);
                        datas.add(new2);
                    }


                    Elements imgHorLinks = docHorizontal.select("div.showcase_img");
                    Elements textHorLinks = docHorizontal.select("div.showcase_text");
                    NewsHorizontal newsHorizontal;
                    for (int j = 0; j < 10; j++) {
                        newsHorizontal = new NewsHorizontal();
                        //标题(OK)
                        String title = textHorLinks.get(j).select("h4").text();
                        newsHorizontal.setNewsTitle(title);
                        Log.e(TAG, "title:" + newsHorizontal.getNewsTitle());
                        //简介(ok)
                        String desc = textHorLinks.get(j).select("div.showcase_info").text();
                        newsHorizontal.setDesc(desc);
                        Log.e(TAG, "desc:" + desc);
                        //网址(OK)
                        String url = imgHorLinks.get(j).select("a").attr("href");
                        newsHorizontal.setNewUrl(url);
                        Log.e(TAG, "url:" + url);
                        //图片(OK)
                        String imgUrl = imgHorLinks.get(j).select("img").attr("src");
                        newsHorizontal.setImgUrl(imgUrl);
                        Log.e(TAG, "imgurl:" + imgUrl);
                        newsHorList.add(newsHorizontal);
                    }
                    Log.e(TAG, "水平结束 ");
                    loading=1;
                }

                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            } catch (IOException e) {
                Toast.makeText(getContext(),"联网失败",Toast.LENGTH_LONG).show();
                Log.e(TAG, "无法连接" );
            }
        }
    }

    class NewsAddThread implements Runnable{

        @Override
        public void run() {
            datasAdd = new ArrayList<>();
            Log.e(TAG, "线程开始" );
            try {
                Log.e(TAG, "开始抓取" );
                Document doc = Jsoup.connect("https://www.gcores.com/categories/1/originals?page="+page).get();
                page++;
                Elements imgLinks = doc.select("div.showcase_img");
                Elements textLinks= doc.select("div.showcase_text");
                New new2;
                for(int j = 0;j < imgLinks.size();j++){
                    new2 = new New();
                    //标题(OK)
                    String title = textLinks.get(j).select("h4").text();
                    new2.setNewsTitle(title);
                    Log.e(TAG, "title:"+title );
                    //简介(ok)
                    String desc = textLinks.get(j).select("div.showcase_info").text();
                    new2.setDesc(desc);
                    Log.e(TAG, "desc:"+desc );
                    //网址(OK)
                    String url = imgLinks.get(j).select("a").attr("href");
                    new2.setNewUrl(url);
                    Log.e(TAG, "url:"+url );
                    //图片(OK)
                    String imgUrl = imgLinks.get(j).select("img").attr("src");
                    new2.setImgUrl(imgUrl);
                    Log.e(TAG, "imgurl:"+imgUrl );
                    datasAdd.add(new2);
                }
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
            } catch (IOException e) {
                Toast.makeText(getContext(),"联网失败",Toast.LENGTH_LONG).show();
                Log.e(TAG, "无法连接" );
            }
        }
    }

    public void setBanner(){
        final Banner banner = (Banner) bannerView.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(bannerImages);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        Banner onBannerListener = banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(bannerUrl.get(position).toString()));
                startActivity(intent);
            }
        });

    };

    private static final String TAG = "MainActivity";



}
