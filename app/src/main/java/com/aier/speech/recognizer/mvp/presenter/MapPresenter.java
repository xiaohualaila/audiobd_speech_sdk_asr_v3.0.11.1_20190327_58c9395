package com.aier.speech.recognizer.mvp.presenter;

import android.util.Log;
import com.aier.speech.recognizer.bean.MapDataResult;
import com.aier.speech.recognizer.bean.MapSearchResult;
import com.aier.speech.recognizer.mvp.contract.MapContract;
import com.aier.speech.recognizer.network.ApiManager;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MapPresenter extends BasePresenter implements MapContract.Persenter {

    private MapContract.View view;

    public MapPresenter(MapContract.View view) {
        this.view = view;
    }


    @Override
    public void loadMapData() {
        ApiManager.getInstence().getAnswerQuestionService()
                .getMapMarker()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MapDataResult>() {

                    @Override
                    public void onError(Throwable e) {
                        view.getDataFail("请求失败！");
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(MapDataResult value) {
                           Log.i("xxx", value.toString());
                        try {
                            if (value.getError_code()==0) {
                                view.getDataSuccess(value.getData());
                            }else {
                                view.getDataFail(value.getError_msg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 地图搜索接口
     * @param str
     */
    @Override
    public void searchData(String str) {
        ApiManager.getInstence().getMapSearchService()
                .getMapSearch(str)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MapSearchResult>() {

                    @Override
                    public void onError(Throwable e) {
                        view.getDataFail("请求失败！");
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(MapSearchResult value) {
                        Log.i("aaa", value.toString());
                        try {
                            if (value.getError_code()==0) {
                                view.getSearchDataSuccess(value.getData());
                            }else {
                                view.getDataFail(value.getError_msg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
