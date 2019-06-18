package com.aier.speech.recognizer;

import android.app.Application;

import com.aier.speech.recognizer.network.NetWorkManager;

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
