package com.baidu.aip.asrwakeup3.mvp.contract;


import com.baidu.aip.asrwakeup3.bean.FaceCheckBean;

/**
 * Created by Zaifeng on 2018/3/1.
 */

public class OpenCVContract {

    public interface Persenter {
        void upLoadPicFile(String pic_path);

    }

    public interface View {
        void getDataSuccess(FaceCheckBean bean);
        void getDataFail();
    }

    public interface Model {

    }

}
