package com.aier.speech.recognizer.mvp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import com.aier.speech.recognizer.broadcastReceiver.NetStateReceiver;
import com.aier.speech.recognizer.util.StatusBarUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    protected Unbinder mUnbinder;
    private Toast mToast;
    private ProgressDialog loadingDialog;
    private NetStateReceiver mStateReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.INSTANCE.setTranslucent(this);
        setContentView(getLayout());
        mContext = this;
        mUnbinder = ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//屏幕常亮
//        initPermission();
        rigisterReceiver();
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
//          drawable-ldpi        120DPI
//          drawable-mdpi        160DPI
//          drawable-hdpi        240DPI
//          drawable-xhdpi       320DPI
//          drawalbe-xxhdpi      480DPI
//          drawable-xxxhdpi     640DPI
        // 小慧机器人 width  1280 height  720 density  2.0 densityDpi  320 drawable-xhdpi
        // 底部导航栏适配
//        if (AndroidWorkaround.Companion.checkDeviceHasNavigationBar(this)) {
//            AndroidWorkaround.Companion.assistActivity(findViewById(android.R.id.content));
//        }
    }

    protected void beforeInit() {

    }


    private void rigisterReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mStateReceiver = new NetStateReceiver();
        registerReceiver(mStateReceiver, filter);
    }

    protected abstract int getLayout();

    protected void finishWithResultOk() {
        setResult(RESULT_OK);
        finish();
    }

    protected BaseActivity getContext() {
        return BaseActivity.this;
    }


    /**
     * 发出一个短Toast
     *
     * @param text 内容
     */
    public void toastShort(String text) {
        toast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 发出一个长toast提醒
     *
     * @param text 内容
     */
    public void toastLong(String text) {
        toast(text, Toast.LENGTH_LONG);
    }


    private void toast(final String text, final int duration) {
        if (!TextUtils.isEmpty(text)) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(getApplicationContext(), text, duration);
                    } else {
                        mToast.setText(text);
                        mToast.setDuration(duration);
                    }
                    mToast.show();
                }
            });
        }
    }

    public void startActiviys(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    public void startActiviys(Class c, int type) {
        Intent intent = new Intent(this, c);
        intent.putExtra("type", type);
        startActivityForResult(intent, type);
    }


    public void startActivityForResult(Class cl, int requestCode) {
        startActivityForResult(new Intent(this, cl), requestCode);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mStateReceiver);
        mUnbinder.unbind();
        super.onDestroy();
    }


}