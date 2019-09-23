package com.aier.speech.recognizer.mvp.contract;

import com.aier.speech.recognizer.bean.SimilarFaceResult;

public class CameraContract {

    public interface Persenter {
        void upLoadPicFile(String pic_path);
        void getTime();
    }

    public interface View {
        void getDataSuccess(SimilarFaceResult value);
        void getDataFail();
        void backTime(String time,String date);
    }

}
