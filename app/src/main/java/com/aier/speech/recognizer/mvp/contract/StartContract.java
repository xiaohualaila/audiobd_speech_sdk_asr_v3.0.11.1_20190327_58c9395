package com.aier.speech.recognizer.mvp.contract;



public class StartContract {

    public interface Persenter {
       void getTime();
    }

    public interface View {
        void backTime(String time);
    }


}
