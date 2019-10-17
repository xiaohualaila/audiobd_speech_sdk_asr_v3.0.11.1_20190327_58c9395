package com.aier.speech.recognizer.mvp.presenter;


import com.aier.speech.recognizer.bean.YUBAIBean2;
import com.aier.speech.recognizer.mvp.contract.ChuxinContract;
import com.aier.speech.recognizer.network.ApiManager;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ChuxinPresenter extends BasePresenter implements ChuxinContract.Persenter {

    private ChuxinContract.View view;

    public ChuxinPresenter(ChuxinContract.View view) {
        this.view = view;
    }


    @Override
    public void loadData(String queryData) {
        ApiManager.getInstence().getYubaiService()
               // .getYUBAIData("YUBAI", queryData)
                .getYUBAIData(queryData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<YUBAIBean2>() {

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
                    public void onNext(YUBAIBean2 value) {
                        //   Log.i("xxx", value.getResult());
                        try {
                            if (value != null) {
                                view.getYubaiDataSuccess(value);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
