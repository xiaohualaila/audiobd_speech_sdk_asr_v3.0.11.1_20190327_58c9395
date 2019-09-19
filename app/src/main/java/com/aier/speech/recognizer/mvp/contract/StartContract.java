package com.aier.speech.recognizer.mvp.contract;



public class StartContract {

    public interface Persenter {
       void getTime();
       void getWeather();
    }

    public interface View {
        void backTime(String time,String date);

        void getWeatherDataSuccess(String weather, String wendu);
    }


}
