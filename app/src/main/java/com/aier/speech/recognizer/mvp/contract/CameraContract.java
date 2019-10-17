package com.aier.speech.recognizer.mvp.contract;

import com.aier.speech.recognizer.bean.QuestionRankResult;
import com.aier.speech.recognizer.bean.SimilarFaceResult;
import com.aier.speech.recognizer.bean.UniqidResult;

public class CameraContract {

    public interface Persenter {
        void upLoadPicFile(String pic_path);
        void upLoadPicGetUseIdFile(String pic_path);
        void getQuestionRank();
    }

    public interface View {
        void getDataSuccess(SimilarFaceResult value);
        void getUniqidDataSuccess(UniqidResult value);
        void getDataFail();
        void getUniqidDataFail();

        void getQuestionRankDataSuccess(QuestionRankResult value);

        void getNetFail();
    }

}
