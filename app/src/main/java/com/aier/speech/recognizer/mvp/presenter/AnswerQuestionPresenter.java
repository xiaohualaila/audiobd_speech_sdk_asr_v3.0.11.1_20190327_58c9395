package com.aier.speech.recognizer.mvp.presenter;

import android.util.Log;

import com.aier.speech.recognizer.bean.AnswerQuestionResult;
import com.aier.speech.recognizer.bean.YUBAIBean;
import com.aier.speech.recognizer.mvp.contract.AnswerQuestionContract;
import com.aier.speech.recognizer.mvp.contract.MainContract;
import com.aier.speech.recognizer.network.ApiManager;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AnswerQuestionPresenter extends BasePresenter implements AnswerQuestionContract.Persenter {

    private AnswerQuestionContract.View view;

    public AnswerQuestionPresenter(AnswerQuestionContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData() {
        ApiManager.getInstence().getAnswerQuestionService()
                .getAnswerQuestion("97bfb41c45f0055e1a7c6c686ca8495e", 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AnswerQuestionResult>() {

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
                    public void onNext(AnswerQuestionResult value) {
                        //Log.i("xxx", value.toString());
                        try {
                            if (value.getError_code()==0) {
                                view.getDataSuccess(value);
                            }else {
                                view.getDataFail();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



}
