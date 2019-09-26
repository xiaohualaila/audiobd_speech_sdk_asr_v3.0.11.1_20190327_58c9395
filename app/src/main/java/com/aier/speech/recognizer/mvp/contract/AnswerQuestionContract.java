package com.aier.speech.recognizer.mvp.contract;


import com.aier.speech.recognizer.bean.AnswerQuestionResult;

public class AnswerQuestionContract {

    public interface Persenter {
        void loadData();

    }

    public interface View {

        void getDataSuccess(AnswerQuestionResult value);

        void getDataFail();
    }


}
