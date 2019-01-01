package cn.edu.hebtu.software.snowcarsh2.radio.bean;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import static android.content.ContentValues.TAG;

public class MusicDate {
    public void date(){
        Document doc = null;
        Log.e(TAG, "date: " );
        try {
            doc = Jsoup.connect("https://www.gcores.com/radios/104740").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.select("div.swiper-slide");
        //时间段总数

//        System.out.println("时间点总数:"+links.size());
        //电台首个图片,没有文字,简介,时间点为0

//        System.out.println("电台大图:"+links.get(0).select("img").attr("src"));
        //for循环遍历获取到每条新闻的四个数据并封装到News实体类中
        for(int j = 1;j < links.size();j++){
            //小标题
            String title = links.get(j).select("h1").text().split( " " )[0];
            Log.e(TAG, "date: 小标题"+title+"/n" );
            //时间点 秒
            String time = links.get(j).select("a").attr("data-at");
            Log.e(TAG, "date: 时间点"+time+"/n" );
            //知识点讲解
            String info = links.get(j).select("p").text();
            Log.e(TAG, "date: 知识点讲解"+info+"/n" );
//            //知识点链接(不一定每个都有)
//            String url = links.get(j).select("p").select("a").attr("href");
//            Log.e(TAG, "date: 小标题"+title+"/n" );
            //知识点配图
            String imgUrlTab =Jsoup.parseBodyFragment(links.get(j).select("textarea").text()).body().select("img").attr("src");
            Log.e(TAG, "date: 知识点配图"+imgUrlTab+"/n" );

        }

    }
}
