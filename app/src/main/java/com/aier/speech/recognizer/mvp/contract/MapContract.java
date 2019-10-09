package com.aier.speech.recognizer.mvp.contract;


import com.aier.speech.recognizer.bean.AllMapResult;
import com.aier.speech.recognizer.bean.EventResult;
import com.aier.speech.recognizer.bean.MapDataResult;
import com.aier.speech.recognizer.bean.MapSearchResult;
import com.aier.speech.recognizer.bean.RenWuResult;

import java.util.List;

public class MapContract {

    public interface Persenter {
        void loadMapData(String tab);
        void searchData(String str);
        void searchJingDianDetailData(String str);//景点
        void searchRenWuDetailData(String str);//人物
        void searchEventMapData(String str);//事件


        void dangzhibuMapBtn();//党支部
    }

    public interface View {
        void getDataSuccess(MapDataResult.DataBean dataBean);
        void getDataFail(String msg);
        void getSearchDataSuccess(MapSearchResult.DataBean bean);
        void getLatAndLngMapSuccess(String lat,String lng,String title);

        void getRenWuMapSuccess(List<RenWuResult.DataBean.ListBean> listBeans);

        void getEventMapSuccess(EventResult.DataBean data);

        void getAllMapSuccess(AllMapResult.DataBean data);
    }


}
