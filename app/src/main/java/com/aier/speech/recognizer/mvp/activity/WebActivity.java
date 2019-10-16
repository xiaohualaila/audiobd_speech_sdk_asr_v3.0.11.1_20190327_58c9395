package com.aier.speech.recognizer.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.aier.speech.recognizer.R;


import butterknife.BindView;

public class WebActivity extends BaseActivity {
    private static final String TAG = "WebActivity";
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.web_title)
    TextView tv_title;
    @BindView(R.id.web_toolbar)
    Toolbar mWebToolbar;
    @BindView(R.id.web_progressBar)
    ProgressBar mWebProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebToolbar.setNavigationOnClickListener(v -> finish());
        //4.2 开启辅助功能崩溃
        initWebSettings();
        String name = "张琴秋";
      //  mWebView.loadUrl("https://www.zq-ai.com/#/redkg?name="+name);
       //mWebView.loadUrl("https://emrys5.github.io/ECharts-Relationship-map/");
       // mWebView.loadUrl("https://gallery.echartsjs.com/editor.html?c=xHy9VHxNuX&v=1");
        mWebView.loadUrl("https://www.zq-ai.com/zqcloudapi/v1.0/nlp/redkgqa?data=毛泽东有什么历史功绩？");
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_web;
    }

    private void initWebSettings() {
        WebSettings settings = mWebView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        settings.setAppCacheMaxSize(1024*1024*8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        //让WebView支持播放插件
        settings.setPluginState(WebSettings.PluginState.ON);
        //设置在WebView内部是否允许访问文件
        settings.setAllowFileAccess(true);
        settings.setDefaultTextEncodingName("utf-8");
       // 开启Application H5 Caches 功能
        settings.setAppCacheEnabled(true);
        mWebView.setWebChromeClient(new MyWebChrome());
        mWebView.setWebViewClient(new MyWebClient());
        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

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
            mWebProgressBar.setVisibility(View.VISIBLE);
            mWebProgressBar.setProgress(newProgress);
        }
    }

    private  class MyWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view,String url) {
            mWebProgressBar.setVisibility(View.GONE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("webview","url: "+url);
            view.loadUrl(url);
            return true;
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
