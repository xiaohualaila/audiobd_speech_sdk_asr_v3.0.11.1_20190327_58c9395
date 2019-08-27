package com.aier.speech.recognizer.mvp.contract;

import com.aier.speech.recognizer.bean.YUBAIBean;

public class MainContract {

    public interface Persenter {
        void loadData(String queryData);

    }

    public interface View {
        void getDataSuccess(YUBAIBean bean);
        void getDataFail();
    }


}
