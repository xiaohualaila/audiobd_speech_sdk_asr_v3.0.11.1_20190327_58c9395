package com.aier.speech.recognizer.mvp.presenter;


import android.text.TextUtils;

import com.aier.speech.recognizer.bean.QuestionRankResult;
import com.aier.speech.recognizer.bean.SimilarFaceResult;
import com.aier.speech.recognizer.bean.UniqidResult;
import com.aier.speech.recognizer.mvp.contract.CameraContract;
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

public class CameraPresenter extends BasePresenter implements CameraContract.Persenter {

    private CameraContract.View view;

    public CameraPresenter(CameraContract.View view) {
        this.view = view;
    }

    @Override
    public void upLoadPicFile(String pic_path) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody requestBody;
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
    public void upLoadPicGetUseIdFile(String pic_path) {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody requestBody;
        if (!TextUtils.isEmpty(pic_path)) {
            File v_file = new File(pic_path);
            if (v_file.exists()) {
                requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), v_file);
                builder.addFormDataPart("file", v_file.getName(), requestBody);
            }
        }

        List<MultipartBody.Part> parts = builder.build().parts();

        ApiManager.getInstence().getAnswerQuestionService()
                .upLoadPicGetUseIdFile("97bfb41c45f0055e1a7c6c686ca8495e",parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UniqidResult>() {

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
                    public void onNext(UniqidResult value) {
                        try {
                            int code =value.getError_code();
                            if(code==0){
                                view.getUniqidDataSuccess(value);
                            }else {
                                view.getUniqidDataFail();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void getQuestionRank() {
        ApiManager.getInstence().getAnswerQuestionService()
                .getQuestionRank()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QuestionRankResult>() {

                    @Override
                    public void onError(Throwable e) {
                        view.getQuestionRankDataFail("网络异常！");
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(QuestionRankResult value) {
                        //Log.i("xxx", value.toString());
                        try {
                            if (value.getError_code()==0) {
                                view.getQuestionRankDataSuccess(value);
                            }else {
                                view.getQuestionRankDataFail(value.getError_msg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



}
