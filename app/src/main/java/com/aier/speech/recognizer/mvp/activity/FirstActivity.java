package com.aier.speech.recognizer.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.aier.speech.recognizer.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import butterknife.BindView;

public class FirstActivity extends  BaseActivity{
    private static String TAG = "FirstActivity";

    @BindView(R.id.tv_info)
    TextView tv_info;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
//        DisplayMetrics metric = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metric);
//        int width = metric.widthPixels;  // 屏幕宽度（像素）
//        int height = metric.heightPixels;  // 屏幕高度（像素）
//        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
//        Log.i("sss","width  "+ width);
//        Log.i("sss","height  "+ height);
//        Log.i("sss","density  "+ density);
//        Log.i("sss","densityDpi  "+ densityDpi);
//        tv_info.setText("width  "+ width+" height  "+ height + " density  "+ density+" densityDpi  "+ densityDpi);
//          drawable-ldpi        120DPI
//          drawable-hdpi        160DPI
//          drawable-hdpi        240DPI
//          drawable-xhdpi       320DPI
//          drawalbe-xxhdpi      480DPI
//          drawable-xxxhdpi     640DPI
        //width 1920 height 1032 density 1.0 dpi 160  adb connect 192.168.99.248
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    Log.i("rr", "OpenCV loaded successfully");
                    startActiviys(CameraActivity.class);
                    finish();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_first;
    }
}
