package com.aier.speech.recognizer.mvp.presenter;

import com.aier.speech.recognizer.bean.YUBAIBean;
import com.aier.speech.recognizer.mvp.contract.MainContract;
import com.aier.speech.recognizer.mvp.model.MainModel;
import com.aier.speech.recognizer.network.request.Request;
import com.aier.speech.recognizer.network.schedulers.BaseSchedulerProvider;
import com.aier.speech.recognizer.network.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter implements MainContract.Persenter {

    private MainContract.View view;

    private CompositeDisposable mDisposable;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        mDisposable = new CompositeDisposable();
    }


    @Override
    public void getYubaiData(String queryData) {
//        try {
//
//            Disposable disposable = model.getYubaiData(queryData)
//                   // .compose(ResponseTransformer.handleResult())
//                    .compose(schedulerProvider.applySchedulers())
//                    .subscribe(bean -> {
//                        try {
//                            if(bean!=null){
//                                view.getDataSuccess(bean);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }, throwable -> {
//                        // 处理异常
//                        view.getDataFail();
//                    });
//
//            mDisposable.add(disposable);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Request.YUYIN_URL)
                .build();
        Request service = retrofit.create(Request.class);
        Call<YUBAIBean> call = service.getWeatherData("YUBAI",queryData);
        call.enqueue(new Callback<YUBAIBean>() {
            @Override
            public void onResponse(Call<YUBAIBean> call, Response<YUBAIBean> response) {
                // 已经转换为想要的类型了
                try {
                    YUBAIBean bean = response.body();
                    if(bean!=null){
                        view.getDataSuccess(bean);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<YUBAIBean> call, Throwable t) {
                view.getDataFail();
            }

        });
    }


}
