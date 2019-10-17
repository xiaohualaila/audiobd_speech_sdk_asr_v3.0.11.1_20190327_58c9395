package com.aier.speech.recognizer.mvp.contract;

import com.aier.speech.recognizer.bean.YUBAIBean2;

public class ChuxinContract {

    public interface Persenter {
        void loadData(String queryData);
    }

    public interface View {

        void getDataFail();

        void getYubaiDataSuccess(YUBAIBean2 DataBean);
    }


}
