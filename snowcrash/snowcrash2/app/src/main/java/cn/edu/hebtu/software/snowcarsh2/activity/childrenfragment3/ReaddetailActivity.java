package cn.edu.hebtu.software.snowcarsh2.activity.childrenfragment3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import cn.edu.hebtu.software.snowcarsh2.R;

public class ReaddetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.childrenfragment3_detail);
        Intent intent=getIntent();
        String LinkUrl=intent.getStringExtra("uri");
        WebView webView=(WebView)findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        //webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(LinkUrl);

    }
}
