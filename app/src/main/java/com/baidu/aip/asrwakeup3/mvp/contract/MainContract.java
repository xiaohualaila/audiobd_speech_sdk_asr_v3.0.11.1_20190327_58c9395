package com.baidu.aip.asrwakeup3.mvp.contract;


import com.baidu.aip.asrwakeup3.bean.YUBAIBean;

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

    }

}
