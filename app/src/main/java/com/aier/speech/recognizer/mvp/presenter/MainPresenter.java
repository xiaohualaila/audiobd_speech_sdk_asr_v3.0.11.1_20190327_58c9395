package com.aier.speech.recognizer.mvp.presenter;

import android.util.Log;
import com.aier.speech.recognizer.bean.YUBAIBean;
import com.aier.speech.recognizer.mvp.contract.MainContract;
import com.aier.speech.recognizer.network.ApiManager;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenter implements MainContract.Persenter {

    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData(String queryData) {
//        ApiManager.getInstence().getYubaiService()
//                .getYUBAIData("YUBAI", queryData)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<YUBAIBean>() {
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.getDataFail();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        addDisposable(d);
//                    }
//
//                    @Override
//                    public void onNext(YUBAIBean value) {
//                     //   Log.i("xxx", value.getResult());
//                        try {
//                            if (value != null) {
//                                view.getDataSuccess(value);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
    }


}
