package com.aier.speech.recognizer.mvp.contract;

public class StartContract {
    public interface Persenter {
        void checkAppVersion();
    }

    public interface View {
        void updateVer(String url);
        void toActivity();
    }

}
