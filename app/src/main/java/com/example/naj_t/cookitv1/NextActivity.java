package com.example.naj_t.cookitv1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.taishi.flipprogressdialog.FlipProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class NextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        Intent i=getIntent();

        if(i.hasExtra("sourceUrl"))
            myWebView.loadUrl(i.getStringExtra("sourceUrl"));
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(false);
        myWebView.setWebViewClient(new WebViewClient());
        progressBar();


    }
    public void progressBar(){
        List<Integer> imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.souchef_medium);
        final FlipProgressDialog border = new FlipProgressDialog();
        border.setImageList(imageList);
        border.setOrientation("rotationY");
        border.setBorderStroke(10);
        border.setDuration(800);
        border.setBorderColor(Color.parseColor("#02A8F3"));
        border.setBackgroundColor(Color.parseColor("#FFFFFF"));
        border.show(getFragmentManager(),"");
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                border.dismiss();
            }
        }, 4000);
    }

}
