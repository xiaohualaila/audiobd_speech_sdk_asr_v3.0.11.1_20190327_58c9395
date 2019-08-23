package com.aier.speech.recognizer.mvp.contract;


import com.aier.speech.recognizer.bean.VersionResult;
import com.aier.speech.recognizer.network.response.Response;

import io.reactivex.Observable;
import okhttp3.RequestBody;


public class StartContract {

    public interface Persenter {
        void checkAppVersion();

    }

    public interface View {
        void updateVer(String url);
        void toActivity();
    }


    public interface Model {
        Observable<Response<VersionResult>> checkAppVersion(RequestBody body);
    }

}
