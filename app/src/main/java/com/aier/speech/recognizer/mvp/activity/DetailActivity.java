package com.aier.speech.recognizer.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.bean.YUBAIBean;
import com.aier.speech.recognizer.mvp.contract.DetailContract;
import com.aier.speech.recognizer.mvp.presenter.DetailPresenter;
import com.aier.speech.recognizer.util.ImageUtils;
import butterknife.BindView;
import butterknife.OnClick;

public class DetailActivity extends RobotSpeechActivity implements DetailContract.View{

    @BindView(R.id.iv_photo)
    ImageView iv_photo;
    @BindView(R.id.name)
    TextView tv_name;
    @BindView(R.id.tv_work)
    TextView tv_work;
    @BindView(R.id.tv_history)
    TextView tv_history;
    @BindView(R.id.tv_score)
    TextView tv_score;
    @BindView(R.id.web_red_people)
    WebView mWebView;
    private DetailPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DetailPresenter(this);

        Bundle bundle = getIntent().getExtras();
        String name =  bundle.getString("name");
        String duty =  bundle.getString("duty");
        String description =  bundle.getString("description");
        String  score =  bundle.getString("score");
        String img =  bundle.getString("img");
        speak("您回到红军时代是" + name + "相似度" + score + "%" + duty);
        ImageUtils.image(this,img,iv_photo);
        tv_name.setText(name);
        tv_work.setText(duty);
        tv_history.setText("   "+description);
        tv_score.setText(score);
      //  presenter.loadData(name);
        initWebSettings();
        mWebView.loadUrl("https://www.zq-ai.com/#/redkg?name="+name);
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

    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }


    @OnClick({R.id.iv_back,R.id.iv_back_,R.id.take_photo,R.id.iv_left_btn,R.id.iv_right_btn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_back_:
                finish();
                break;
            case R.id.take_photo:
                finish();
                break;
            case R.id.iv_left_btn://初心地图
                startActiviys(MapActivity.class);
                finish();
                break;
            case R.id.iv_right_btn://菜单
                startActiviys(MenuActivity.class);
                finish();
                break;
        }
    }

    @Override
    public void getDataSuccess(YUBAIBean DataBean) {

    }

    @Override
    public void getDataFail() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }


    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }
}
