package com.aier.speech.recognizer.mvp.presenter;

import com.aier.speech.recognizer.mvp.contract.MainContract;
import com.aier.speech.recognizer.mvp.model.MainModel;
import com.aier.speech.recognizer.network.schedulers.BaseSchedulerProvider;
import com.aier.speech.recognizer.network.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainPresenter implements MainContract.Persenter {
    private MainModel model;

    private MainContract.View view;

    private BaseSchedulerProvider schedulerProvider;

    private CompositeDisposable mDisposable;

    public MainPresenter(MainModel model,MainContract.View view,
                         SchedulerProvider schedulerProvider) {
        this.model = model;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        mDisposable = new CompositeDisposable();

    }

    public void despose(){
        mDisposable.dispose();
    }

    @Override
    public void getYubaiData(String queryData) {
        try {

            Disposable disposable = model.getYubaiData(queryData)
                   // .compose(ResponseTransformer.handleResult())
                    .compose(schedulerProvider.applySchedulers())
                    .subscribe(bean -> {
                        try {
                            if(bean!=null){
                                view.getDataSuccess(bean);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, throwable -> {
                        // 处理异常
                        view.getDataFail();
                    });

            mDisposable.add(disposable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
