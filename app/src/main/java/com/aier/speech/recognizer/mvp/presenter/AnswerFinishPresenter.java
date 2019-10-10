package com.aier.speech.recognizer.mvp.presenter;

import android.util.Log;

import com.aier.speech.recognizer.bean.AddQuestionResult;
import com.aier.speech.recognizer.bean.YUBAIBean;
import com.aier.speech.recognizer.mvp.contract.AnswerFinishContract;
import com.aier.speech.recognizer.network.ApiManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class AnswerFinishPresenter extends BasePresenter implements AnswerFinishContract.Persenter {

    private AnswerFinishContract.View view;

    public AnswerFinishPresenter(AnswerFinishContract.View view  ) {
        this.view = view;
    }



    @Override
    public void upLoadScore(String uniqid, String score) {
        ApiManager.getInstence().getAnswerQuestionService()
                .addQuestionResult(uniqid,score)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddQuestionResult>() {

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onComplete() { }

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(AddQuestionResult value) {
                        try {
                            if (value.getError_code()==0) {
                                view.getDataSuccess();
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
