package com.aier.speech.recognizer.mvp.presenter;

import android.util.Log;
import com.aier.speech.recognizer.bean.VersionResult;
import com.aier.speech.recognizer.mvp.contract.StartContract;
import com.aier.speech.recognizer.mvp.model.StartModel;
import com.aier.speech.recognizer.network.response.ResponseTransformer;
import com.aier.speech.recognizer.network.schedulers.BaseSchedulerProvider;
import com.aier.speech.recognizer.network.schedulers.SchedulerProvider;
import com.aier.speech.recognizer.util.PackageUtil;
import org.json.JSONException;
import org.json.JSONObject;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class StartPresenter implements StartContract.Persenter {
    private StartModel model;

    private StartContract.View view;

    private BaseSchedulerProvider schedulerProvider;

    private CompositeDisposable mDisposable;

    public StartPresenter(StartModel model, StartContract.View view,
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
    public void checkAppVersion() {
        try {
            JSONObject obj =new JSONObject();
            JSONObject obj1 =new JSONObject();
            obj.put("method","NKCLOUDAPI_GETLASTAPP");
            obj1.put("appname","羽白");
            obj.put("params",obj1);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());
            Disposable disposable = model.checkAppVersion(body)
                    .compose(ResponseTransformer.handleResult())
                    .compose(schedulerProvider.applySchedulers())
                    .subscribe(response -> {
                              Log.i("sssss", response.toString());
                        if(response.isSuccess()){
                            VersionResult.DataBean bean = response.getData();
                            String ver =PackageUtil.INSTANCE.getVersionName();
                            if(!bean.getVersion().equals(ver)){
                                view.updateVer(bean.getUrl());
                            }else {
                                view.toActivity();
                            }
                        }else {
                            view.toActivity();
                        }
                    }, throwable -> {
                        view.toActivity();
                    });

            mDisposable.add(disposable);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
