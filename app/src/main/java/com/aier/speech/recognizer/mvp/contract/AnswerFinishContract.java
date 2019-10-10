package com.aier.speech.recognizer.mvp.contract;



public class AnswerFinishContract {

    public interface Persenter {

        void upLoadScore(String uniqid,String score);
    }

    public interface View {
        void getDataFail();

        void getDataSuccess();
    }


}
