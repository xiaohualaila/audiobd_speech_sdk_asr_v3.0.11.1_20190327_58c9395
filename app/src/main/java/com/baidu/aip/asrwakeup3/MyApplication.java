package com.baidu.aip.asrwakeup3;

import android.app.Application;

import com.baidu.aip.asrwakeup3.network.NetWorkManager;

public class MyApplication extends Application {

    private static Application appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        NetWorkManager.getInstance().init();
    }

    public static Application getAppContext() {
        return appContext;
    }
}
