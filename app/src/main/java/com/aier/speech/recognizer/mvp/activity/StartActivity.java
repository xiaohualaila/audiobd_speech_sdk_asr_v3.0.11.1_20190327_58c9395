package com.aier.speech.recognizer.mvp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import com.aier.speech.recognizer.BuildConfig;
import com.aier.speech.recognizer.R;
import com.aier.speech.recognizer.dialog.Apk_dialog;
import com.aier.speech.recognizer.mvp.contract.StartContract;
import com.aier.speech.recognizer.mvp.presenter.StartPresenter;
import com.aier.speech.recognizer.weight.AppDownload;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StartActivity extends BaseActivity implements StartContract.View, AppDownload.Callback {
    private static String TAG = "StartActivity";
    private StartPresenter presenter;
    private String path;
    Apk_dialog apk_dialog;
    SeekBar seek;
    TextView numProBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission();
        presenter = new StartPresenter( this);
    }

    private void requestPermission() {
        // 6.0以下版本直接同意使用权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasNecessaryPermission()) {
                ActivityCompat.requestPermissions(this, ALL_PERMISSIONS, 1123);
            } else {
                presenter.checkAppVersion();
            }
        } else {
            presenter.checkAppVersion();
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
            Manifest.permission.CHANGE_WIFI_STATE
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
                    Log.i(TAG, "权限申请成功！");
                    presenter.checkAppVersion();
                } else {
                    Log.i(TAG, "权限申请失败！");
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

    @Override
    public void updateVer(String url) {

        path = Environment.getExternalStorageDirectory() + "/yubai/" + "羽白.apk";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        apk_dialog = new Apk_dialog(StartActivity.this);
        if (apk_dialog != null && apk_dialog.isShowing()) {
            return;
        }

        AppDownload appDownload = new AppDownload();
        appDownload.setProgressInterface(StartActivity.this);
        appDownload.downApk(url, StartActivity.this);
        apk_dialog.show();
        apk_dialog.setCancelable(false);
        seek = apk_dialog.getSeekBar();
        numProBar = apk_dialog.getNumProBar();
    }

    @Override
    public void toActivity() {
        startDelay();
    }

    @Override
    public void callProgress(int progress) {
        if (progress >= 100) {
            runOnUiThread(() -> {
                apk_dialog.dismiss();
                install(path);
            });

        } else {
            runOnUiThread(() -> {
                seek.setProgress(progress);
                numProBar.setText(progress + "%");
            });
        }
    }

    /**
     * 开启安装过程
     *
     * @param fileName
     */
    private void install(String fileName) {

        File file = new File(fileName);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //判读版本是否在7.0以上
        Uri apkUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            apkUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", file);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            apkUri = Uri.fromFile(file);
        }
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}
