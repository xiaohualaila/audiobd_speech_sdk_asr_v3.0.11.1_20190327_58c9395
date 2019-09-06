package com.aier.speech.recognizer.mvp.contract;

import com.aier.speech.recognizer.bean.SimilarFaceResult;

public class OpenCVContract {

    public interface Persenter {
        void upLoadPicFile(String pic_path);
    }

    public interface View {
        void getDataSuccess(SimilarFaceResult value);
        void getDataFail();
    }

}
