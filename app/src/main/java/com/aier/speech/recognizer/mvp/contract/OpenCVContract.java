package com.aier.speech.recognizer.mvp.contract;


import com.aier.speech.recognizer.bean.FaceCheckBean;

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
