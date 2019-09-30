package com.aier.speech.recognizer.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.aier.speech.recognizer.R;


import butterknife.BindView;

public class WebActivity extends BaseActivity {
    private static final String TAG = "WebActivity";
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.iv_back)
    ImageView iv_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         int type= getIntent().getIntExtra("type",1);
        //4.2 开启辅助功能崩溃
        initWebSettings();
        if(type==1){
            mWebView.loadUrl("https://720yun.com/t/b2vkiy2mr7b?scene_id=34359372");
        }else if(type==2){
            mWebView.loadUrl("https://720yun.com/t/b2vkiy2mr7b?scene_id=34359373");
        }else {
            mWebView.loadUrl("https://720yun.com/t/b2vkiy2mr7b?scene_id=34359374");
        }

        iv_back.setOnClickListener(v -> finish());
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_web;
    }

    private void initWebSettings() {
        WebSettings settings = mWebView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        mWebView.setWebChromeClient(new MyWebChrome());
        mWebView.setWebViewClient(new MyWebClient());
    }

    @Override
     public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    private  class MyWebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

        }
    }

    private  class MyWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view,String url) {

        }
    }


    public static final void  startActivity(Context context,String title,String content,int type){
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("codedContent",content);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }


}
