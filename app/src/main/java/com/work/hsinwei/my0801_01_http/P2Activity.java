package com.work.hsinwei.my0801_01_http;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class P2Activity extends AppCompatActivity {
    WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2);
        Intent it=getIntent();
        String str= it.getStringExtra("link");
        /*
        TextView tv2=(TextView)findViewById(R.id.textView2);
        tv2.setText(str);*/
        wv=(WebView)findViewById(R.id.webView);

        wv.getSettings().setJavaScriptEnabled(true);//JavaScript 功能啟用

        wv.getSettings().setSupportZoom(true);  //支援縮放
        wv.getSettings().setBuiltInZoomControls(true); //開啟網頁內容縮放

        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl(str);

    }
    /*
    @Override
    public void onBackPressed(){
        //if(wv.getUrl().contains(""))
        wv.goBack();
    }*/
}
