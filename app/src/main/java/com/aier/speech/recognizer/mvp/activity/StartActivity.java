package com.aier.speech.recognizer.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
    public void returnRedTime(View view) {
        if(openCVIsOk){
            startActiviys(Camera2Activity.class);
        }else {
            ToastyUtil.INSTANCE.showError("openCV初始化失败！，请重启软件。");
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
