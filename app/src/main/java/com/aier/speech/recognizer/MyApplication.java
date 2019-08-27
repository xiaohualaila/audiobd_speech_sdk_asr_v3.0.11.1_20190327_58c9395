package com.aier.speech.recognizer;

import android.app.Application;

import com.aier.speech.recognizer.util.Utils;

public class MyApplication extends Application {

    private static Application appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        Utils.Companion.init(this);
    }

    public static Application getAppContext() {
        return appContext;
    }
}
