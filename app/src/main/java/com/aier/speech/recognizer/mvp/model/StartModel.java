package com.aier.speech.recognizer.mvp.model;

import com.aier.speech.recognizer.bean.VersionResult;
import com.aier.speech.recognizer.mvp.contract.StartContract;
import com.aier.speech.recognizer.network.NetWorkManager;
import com.aier.speech.recognizer.network.response.Response;
import io.reactivex.Observable;
import okhttp3.RequestBody;

public class StartModel implements StartContract.Model{

    @Override
    public Observable<Response<VersionResult>> checkAppVersion(RequestBody body) {
        return NetWorkManager.getRequest().getVerDataForBody(body);
    }
}
