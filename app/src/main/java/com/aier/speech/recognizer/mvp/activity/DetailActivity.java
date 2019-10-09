package com.aier.speech.recognizer.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.bean.YUBAIBean;
import com.aier.speech.recognizer.mvp.contract.DetailContract;
import com.aier.speech.recognizer.mvp.presenter.DetailPresenter;
import com.aier.speech.recognizer.util.ImageUtils;
import com.google.android.flexbox.FlexboxLayout;

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
    @BindView(R.id.flexbox)
    FlexboxLayout layout;
    private DetailPresenter presenter;
    String[] strings = {"寒食过", "云雨消", "不夜侯正好", "又是一年", "采茶时节暖阳照", "风追着",
            "蝴蝶跑", "谁家种红苕", "木犁松土", "地龙惊兮蚁出巢", "翠盈盈", "悠香飘"};

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
        addFlexBox();
    }

    //添加标签
    private void addFlexBox() {

        for (int i = 0; i < strings.length; i++) {
            TextView textView = new TextView(this);
            FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(15, 10, 15, 10);

            if(i==2){
                textView.setTextColor(getResources().getColor(R.color.red_btn));
                textView.setTextSize(20f);
            }else if(i==4){
                textView.setTextColor(getResources().getColor(R.color.yellow));
                textView.setTextSize(24f);
            }else if(i==6){
                textView.setTextColor(getResources().getColor(R.color.red));
                textView.setTextSize(22f);
            }else {
                textView.setTextColor(getResources().getColor(R.color.text_color_gary));
                textView.setTextSize(26f);
            }
            textView.setText(strings[i]);

            textView.setPadding(8, 5, 8, 0);
            textView.setLayoutParams(layoutParams);
            layout.addView(textView);

        }
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
