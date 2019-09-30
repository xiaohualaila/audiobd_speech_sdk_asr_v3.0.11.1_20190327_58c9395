package com.aier.speech.recognizer.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.mvp.contract.StartContract;
import com.aier.speech.recognizer.mvp.presenter.StartPresenter;
import com.aier.speech.recognizer.util.ToastyUtil;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import butterknife.BindView;
import butterknife.OnClick;

public class StartActivity extends BaseActivity implements StartContract.View {
    private static String TAG = "StartActivity";
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_wendu)
    TextView tv_wendu;
    @BindView(R.id.tv_weather)
    TextView tv_weather;
    @BindView(R.id.shebei)
    TextView tv_shebei;
    private StartPresenter presenter;
    private boolean openCVIsOk = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StartPresenter(this);
        presenter.getTime();
        presenter.getWeather();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
       // startDelay();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        String shebei = "width " + width +" "+" height "+height+" 屏幕密度 " + density + " densityDpi " +densityDpi;
        tv_shebei.setText(shebei);
    }



    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    Log.i("rr", "OpenCV loaded successfully");
                    openCVIsOk = true;
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    private void startDelay() {
        new Handler().postDelayed(() -> {
            startActiviys(MainActivity.class);
            finish();
        }, 2000);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_start;
    }

    public void openIv1(View view) {
       // startActivity(new Intent(this, .class));
    }
    public void openIv2(View view) {
     //   startActivity(new Intent(this, .class));
    }
    public void openIv3(View view) {
      //  startActivity(new Intent(this, .class));
    }
    public void openIv4(View view) {
     //   startActivity(new Intent(this, .class));
    }
    public void openIv5(View view) {
      //  startActivity(new Intent(this, .class));
    }
    public void openIv6(View view) {
       // startActivity(new Intent(this, .class));
    }




    @OnClick({R.id.iv_1, R.id.iv_2, R.id.iv_3,R.id.iv_4,R.id.iv_5,R.id.iv_6,R.id.iv_7})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_1:

                break;
            case R.id.iv_2:
                startActiviys(MapActivity.class);
                break;
            case R.id.iv_3:
                startActiviys(AnswerQuestionActivity.class);
                break;
            case R.id.iv_4:

                break;

            case R.id.iv_5:

                break;
            case R.id.iv_6:

                break;
            case R.id.iv_7:
                if(openCVIsOk){
                    startActiviys(Camera2Activity.class);
                }else {
                    ToastyUtil.INSTANCE.showError("openCV初始化失败！，请重启软件。");
                }
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }

    @Override
    public void backTime(String time,String date) {
        tv_time.setText(time);
        tv_date.setText(date);
    }

    @Override
    public void getWeatherDataSuccess(String weather, String wendu) {
        tv_weather.setText(weather);
        tv_wendu.setText(wendu);
    }
}
