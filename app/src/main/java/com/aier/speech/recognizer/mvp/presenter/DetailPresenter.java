package com.aier.speech.recognizer.mvp.presenter;

import android.util.Log;

import com.aier.speech.recognizer.bean.GuanlianCitiaoResult;
import com.aier.speech.recognizer.mvp.contract.DetailContract;
import com.aier.speech.recognizer.network.ApiManager;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class DetailPresenter extends BasePresenter implements DetailContract.Persenter {

    private DetailContract.View view;

    public DetailPresenter(DetailContract.View view) {
        this.view = view;
    }


    @Override
    public void loadData(String queryData) {
        ApiManager.getInstence().getMapSearchService()
                .getFigureRelate(queryData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GuanlianCitiaoResult>() {

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
                    public void onNext(GuanlianCitiaoResult value) {
                        try {
                            if (value.getError_code()==0) {
                                List<GuanlianCitiaoResult.DataBean.RelateBean> relateBean =  value.getData().getRelate();
                                ArrayList<String> list = new ArrayList<>();
                                for (int i=0;i<relateBean.size();i++){
                                    list.add(relateBean.get(i).getName());
                                }
                                view.getDataSuccess(list);
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
