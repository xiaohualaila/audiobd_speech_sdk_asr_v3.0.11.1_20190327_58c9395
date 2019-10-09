package com.aier.speech.recognizer.mvp.contract;


import java.util.List;

public class DetailContract {

    public interface Persenter {
        void loadData(String queryData);

    }

    public interface View {
        void getDataSuccess(List<String> strings);
        void getDataFail();

    }


}
