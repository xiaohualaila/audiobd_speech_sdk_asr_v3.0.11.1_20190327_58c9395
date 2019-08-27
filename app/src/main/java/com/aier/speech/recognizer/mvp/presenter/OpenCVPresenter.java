package com.aier.speech.recognizer.mvp.presenter;


import android.text.TextUtils;

import com.aier.speech.recognizer.bean.FaceCheckBean;
import com.aier.speech.recognizer.mvp.contract.OpenCVContract;
import com.aier.speech.recognizer.network.ApiManager;
import java.io.File;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class OpenCVPresenter extends BasePresenter implements OpenCVContract.Persenter {

    private OpenCVContract.View view;

    public OpenCVPresenter(OpenCVContract.View view) {
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
                .uploadPicFile(parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FaceCheckBean>() {

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
                    public void onNext(FaceCheckBean value) {
                        try {
                       //     Log.i("xxx", value.toString());
                            view.getDataSuccess(value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
