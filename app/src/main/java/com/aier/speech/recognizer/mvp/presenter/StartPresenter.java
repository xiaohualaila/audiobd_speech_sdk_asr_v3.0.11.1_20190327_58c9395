package com.aier.speech.recognizer.mvp.presenter;
import android.content.Context;
import com.aier.speech.recognizer.mvp.contract.StartContract;
import java.util.Calendar;
import java.util.TimeZone;

public class StartPresenter extends BasePresenter implements StartContract.Persenter {

    private StartContract.View view;
    private Context context;

    public StartPresenter(StartContract.View view  ) {
        this.view = view;
        this.context = context;
    }


    @Override
    public void getTime() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        if(mMonth.length()==1){
            mMonth ="0"+mMonth;
        }
        String  mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        view.backTime(hour+ ":" +minute +" "+mMonth +"/" + mDay+" 周"+mWay);
    }
}
