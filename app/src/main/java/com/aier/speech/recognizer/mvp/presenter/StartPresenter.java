package com.aier.speech.recognizer.mvp.presenter;
import android.util.Log;
import com.aier.speech.recognizer.mvp.contract.StartContract;
import java.util.Calendar;
import java.util.TimeZone;


public class StartPresenter extends BasePresenter implements StartContract.Persenter {

    private StartContract.View view;

    public StartPresenter(StartContract.View view  ) {
        this.view = view;
    }


    @Override
    public void getTime() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String year = String.valueOf(c.get(Calendar.YEAR));
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        if(mMonth.length()==1){
            mMonth ="0"+mMonth;
        }
        String  mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        if(mDay.length()==1){
            mDay ="0"+mDay;
        }

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        view.backTime(hour+ ":" +minute,year+"-"+mMonth +"-" + mDay);
    }
}
