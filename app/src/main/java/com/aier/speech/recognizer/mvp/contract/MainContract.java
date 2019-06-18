package com.aier.speech.recognizer.mvp.contract;


import com.aier.speech.recognizer.bean.YUBAIBean;

import io.reactivex.Observable;

/**
 * Created by Zaifeng on 2018/3/1.
 */

public class MainContract {

    public interface Persenter {
        void getYubaiData(String queryData);

    }

    public interface View {
        void getDataSuccess(YUBAIBean bean);
        void getDataFail();
    }

    public interface Model {
        Observable<YUBAIBean> getYubaiData(String data);
    }

}
