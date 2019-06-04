package com.baidu.aip.asrwakeup3.mvp.presenter;


import com.baidu.aip.asrwakeup3.bean.YUBAIBean;
import com.baidu.aip.asrwakeup3.mvp.contract.MainContract;
import com.baidu.aip.asrwakeup3.network.URLConstant;
import com.baidu.aip.asrwakeup3.network.request.Request;
import com.baidu.aip.asrwakeup3.network.schedulers.BaseSchedulerProvider;
import com.baidu.aip.asrwakeup3.network.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter implements MainContract.Persenter{

    private MainContract.View view;

    private BaseSchedulerProvider schedulerProvider;

    private CompositeDisposable mDisposable;

    public MainPresenter(MainContract.View view,
                         SchedulerProvider schedulerProvider) {
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        mDisposable = new CompositeDisposable();

    }

    public void despose(){
        mDisposable.dispose();
    }

    @Override
    public void getYubaiData(String data) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URLConstant.YUYIN_URL)
                .build();
        Request service = retrofit.create(Request.class);
        Call<YUBAIBean> call = service.getWeatherData("YUBAI",data);
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
