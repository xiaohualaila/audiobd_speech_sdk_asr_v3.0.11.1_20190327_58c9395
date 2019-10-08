package com.aier.speech.recognizer.mvp.presenter;

import android.util.Log;

import com.aier.speech.recognizer.bean.EventResult;
import com.aier.speech.recognizer.bean.JingdianResult;
import com.aier.speech.recognizer.bean.MapDataResult;
import com.aier.speech.recognizer.bean.MapSearchResult;
import com.aier.speech.recognizer.bean.RenWuResult;
import com.aier.speech.recognizer.mvp.contract.MapContract;
import com.aier.speech.recognizer.network.ApiManager;

import java.util.List;

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
                .getMapMarker("app")
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
                .getMapSearch(str,"app")
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

    @Override
    public void searchJingDianDetailData(String str) {
        ApiManager.getInstence().getMapSearchService()
                .getJingDianMapSearch(str,"app")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JingdianResult>() {

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
                    public void onNext(JingdianResult value) {
                        Log.i("aaa", value.toString());
                        try {
                            if (value.getError_code()==0) {
                               JingdianResult.DataBean.NewsInfoBean newsInfoBean = value.getData().getNews_info();
                                if(newsInfoBean!=null){
                                    String lat = value.getData().getNews_info().getLat();
                                    String lng = value.getData().getNews_info().getLng();
                                    String title = value.getData().getNews_info().getTitle();
                                    view.getLatAndLngMapSuccess(lat,lng,title);
                                }
                            }else {
                                view.getDataFail(value.getError_msg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    public void searchRenWuDetailData(String str) {
        ApiManager.getInstence().getMapSearchService()
                .getRenWuMapSearch(str,"app")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RenWuResult>() {

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
                    public void onNext(RenWuResult value) {
                        Log.i("aaa", value.toString());
                        try {
                            if (value.getError_code()==0) {
                                List<RenWuResult.DataBean.ListBean> listBeans = value.getData().getList();
                                view.getRenWuMapSuccess(listBeans);
                            }else {
                                view.getDataFail(value.getError_msg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    public void searchEventMapData(String str) {
        ApiManager.getInstence().getMapSearchService()
                .getEventMapSearch(str,"app")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EventResult>() {

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
                    public void onNext(EventResult value) {
                        Log.i("aaa", value.toString());
                        try {
                            if (value.getError_code()==0) {
                                view.getEventMapSuccess(value.getData());
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
