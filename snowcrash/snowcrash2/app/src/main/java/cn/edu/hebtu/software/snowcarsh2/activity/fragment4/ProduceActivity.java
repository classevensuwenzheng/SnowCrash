package cn.edu.hebtu.software.snowcarsh2.activity.fragment4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import cn.edu.hebtu.software.snowcarsh2.R;

public class ProduceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment4_detail);
        Intent intent=getIntent();
        String LinkUrl=intent.getStringExtra("uri");

        WebView webView=(WebView)findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.loadUrl(LinkUrl);
    }
}
