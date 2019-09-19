package com.aier.speech.recognizer.mvp.presenter;
import android.util.Log;

import com.aier.speech.recognizer.bean.SimilarFaceResult;
import com.aier.speech.recognizer.bean.YUBAIBean;
import com.aier.speech.recognizer.mvp.contract.StartContract;
import com.aier.speech.recognizer.network.ApiManager;

import java.util.Calendar;
import java.util.TimeZone;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

    @Override
    public void getWeather() {
        ApiManager.getInstence().getYubaiService()
                .getYUBAIData("YUBAI","今天天气怎样")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<YUBAIBean>() {

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(YUBAIBean value) {
                        try {
                            if(value!=null){
                                String result =value.getResult();
                                String result2 =value.getResult();
                                int index =result.indexOf(":");
                                int index1 = result.indexOf(",");
                                String str = result.substring(index+1,index1);
                                String[] weather =str.split("，");

                                String wendu = result2.substring(result.lastIndexOf("℃")-2,result.lastIndexOf("℃")+1);
                                Log.i("sss","array  "+weather[0].trim());
                                Log.i("sss","wendu  "+wendu);
                                view.getWeatherDataSuccess(weather[0],wendu.trim());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



}
