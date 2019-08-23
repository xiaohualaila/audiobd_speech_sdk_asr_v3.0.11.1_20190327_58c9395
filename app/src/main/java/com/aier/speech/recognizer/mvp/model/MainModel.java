package com.aier.speech.recognizer.mvp.model;

import com.aier.speech.recognizer.network.NetWorkManager;
import com.aier.speech.recognizer.bean.YUBAIBean;
import com.aier.speech.recognizer.mvp.contract.MainContract;
import io.reactivex.Observable;

public class MainModel implements MainContract.Model{


    @Override
    public Observable<YUBAIBean> getYubaiData(String data) {
        return NetWorkManager.getRequest().getYUBAIData("YUBAI",data);
    }


}
