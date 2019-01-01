package cn.edu.hebtu.software.snowcarsh2.activity.childrenfragment2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.edu.hebtu.software.snowcarsh2.R;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.childrenfragment2_detail);
        Intent intent=getIntent();
        String LinkUrl=intent.getStringExtra("uri");

        WebView webView=(WebView)findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);


        //webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(LinkUrl);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


                //编写 javaScript方法
                String javascript =  "javascript:function hideOther() {" +
                        "var headers = document.getElementsByClassName('navbar_header');" +

                        "var firstHeader = headers[0];" +

                        "firstHeader.remove();" +

                        "var footers = document.getElementsByClassName('footer');" +

                        "var firstFooter = footers[0];" +

                        "firstFooter.remove();" +
                        "var apps = document.getElementsByClassName('a_appBtmTip');" +


                        "var firstApp = apps[0];" +

                        "firstApp.remove();"+
                        "var divs = document.getElementsByClassName('navbar navbar-default navbar-fixed-top');" +

                        "var firstDiv = divs[0];" +
                        "firstDiv.remove()"+


                        "var div2s = document.getElementsByClassName('text-center');" +

                        "var firstDiv2 = div2s[0];" +
                        "firstDiv2.remove();"+

                        "var logos = document.getElementsByClassName('navbar_brand navbar_brand-logo');" +

                        "var logo = logos[0];" +

                        "logo.remove();"+

                        "}";

                //创建方法
                view.loadUrl(javascript);

                //加载方法
                view.loadUrl("javascript:hideOther();");
            }
        });


    }
}
