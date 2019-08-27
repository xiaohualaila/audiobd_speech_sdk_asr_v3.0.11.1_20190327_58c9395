package com.aier.speech.recognizer.mvp.contract;

import com.aier.speech.recognizer.bean.FaceCheckBean;

public class OpenCVContract {

    public interface Persenter {
        void upLoadPicFile(String pic_path);
    }

    public interface View {
        void getDataSuccess(FaceCheckBean bean);
        void getDataFail();
    }

}
