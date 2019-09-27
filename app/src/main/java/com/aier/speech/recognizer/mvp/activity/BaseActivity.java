package com.aier.speech.recognizer.mvp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import com.aier.speech.recognizer.broadcastReceiver.NetStateReceiver;
import com.aier.speech.recognizer.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    protected Unbinder mUnbinder;
    private Toast mToast;
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
        requestPermission();
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

    private void requestPermission() {
        // 6.0以下版本直接同意使用权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasNecessaryPermission()) {
                ActivityCompat.requestPermissions(this, ALL_PERMISSIONS, 1123);
            }
        }
    }

    String[] ALL_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private boolean hasNecessaryPermission() {
        List<String> permissionsList = new ArrayList();
        for (String permission : ALL_PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
            }
        }
        return permissionsList.size() == 0;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1123: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   // Log.i(TAG, "权限申请成功！");
                } else {
                  //  Log.i(TAG, "权限申请失败！");
                    showMissingPermissionDialog();
                }
                return;
            }
        }
    }


    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-\"权限\"-打开所需权限。");
        // 拒绝, 退出应用
        builder.setNegativeButton("取消", (dialog, which) -> finish());
        builder.setPositiveButton("设置", (dialog, which) -> startAppSettings());
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }



}