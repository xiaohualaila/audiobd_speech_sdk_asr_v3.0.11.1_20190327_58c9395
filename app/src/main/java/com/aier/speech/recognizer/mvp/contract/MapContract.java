package com.aier.speech.recognizer.mvp.contract;


import com.aier.speech.recognizer.bean.MapDataResult;
import com.aier.speech.recognizer.bean.MapSearchResult;

public class MapContract {

    public interface Persenter {
        void loadMapData();
        void searchData(String str);
    }

    public interface View {
        void getDataSuccess(MapDataResult.DataBean dataBean);
        void getDataFail(String msg);
        void getSearchDataSuccess(MapSearchResult.DataBean bean);
    }


}
