package com.aier.speech.recognizer.mvp.presenter;


import android.text.TextUtils;

import com.aier.speech.recognizer.bean.SimilarFaceResult;
import com.aier.speech.recognizer.mvp.contract.CameraContract;
import com.aier.speech.recognizer.network.ApiManager;
import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CameraPresenter extends BasePresenter implements CameraContract.Persenter {

    private CameraContract.View view;

    public CameraPresenter(CameraContract.View view) {
        this.view = view;
    }

    @Override
    public void upLoadPicFile(String pic_path) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody requestBody = null;
        if (!TextUtils.isEmpty(pic_path)) {
            File v_file = new File(pic_path);
            if (v_file.exists()) {
                requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), v_file);
                builder.addFormDataPart("file", v_file.getName(), requestBody);
            }
        }

        List<MultipartBody.Part> parts = builder.build().parts();

        ApiManager.getInstence().getCheckFaceService()
                .uploadRedPeoplePicFile(parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimilarFaceResult>() {

                    @Override
                    public void onError(Throwable e) {
                        view.getDataFail();
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(SimilarFaceResult value) {
                        try {
                            view.getDataSuccess(value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
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
        if(mDay.length()==1){
            mDay ="0"+mDay;
        }
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        view.backTime(hour+ ":" +minute,mMonth +"/" + mDay+" "+weekDays[c.get(Calendar.DAY_OF_WEEK)-1]);
    }
}
