package com.aier.speech.recognizer.mvp.contract;

import com.aier.speech.recognizer.bean.FaceCheckBean;
import com.aier.speech.recognizer.bean.SimilarFaceResult;

public class OpenCVContract {

    public interface Persenter {
        void upLoadPicFile(String pic_path);
        void upLoadToRedPeopleFile(String pic_path);
    }

    public interface View {
        void getDataSuccess(FaceCheckBean bean);

        void getRedDataSuccess(SimilarFaceResult bean);
        void getDataFail();
    }

}
