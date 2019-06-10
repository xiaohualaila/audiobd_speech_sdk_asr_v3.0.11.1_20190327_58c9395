package com.baidu.aip.asrwakeup3.mvp.model;

import com.baidu.aip.asrwakeup3.bean.YUBAIBean;
import com.baidu.aip.asrwakeup3.mvp.contract.MainContract;
import com.baidu.aip.asrwakeup3.network.NetWorkManager;
import io.reactivex.Observable;

public class MainModel implements MainContract.Model{


    @Override
    public Observable<YUBAIBean> getYubaiData(String data) {
        return NetWorkManager.getRequest().getYUBAIData("YUBAI",data);
    }


}
