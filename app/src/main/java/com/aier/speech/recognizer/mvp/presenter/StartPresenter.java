package com.aier.speech.recognizer.mvp.presenter;

import com.aier.speech.recognizer.bean.VersionResult;
import com.aier.speech.recognizer.mvp.contract.StartContract;
import com.aier.speech.recognizer.network.ApiManager;
import com.aier.speech.recognizer.network.response.Response;
import com.aier.speech.recognizer.util.PackageUtil;
import org.json.JSONException;
import org.json.JSONObject;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class StartPresenter extends BasePresenter implements StartContract.Persenter {
    private StartContract.View view;

    public StartPresenter(StartContract.View view) {
        this.view = view;
    }


    @Override
    public void checkAppVersion() {
        try {
            JSONObject obj = new JSONObject();
            JSONObject obj1 = new JSONObject();
            obj.put("method", "NKCLOUDAPI_GETLASTAPP");
            obj1.put("appname", "羽白");
            obj.put("params", obj1);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());
            ApiManager.getInstence().getCommonService()
                    .getVerDataForBody(body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<VersionResult>>() {
                        @Override
                        public void onError(Throwable e) {
                            view.toActivity();
                        }

                        @Override
                        public void onComplete() {
                        }

                        @Override
                        public void onSubscribe(Disposable d) {
                            addDisposable(d);
                        }

                        @Override
                        public void onNext(Response<VersionResult> response) {
                        //    Log.i("sssss", response.toString());
                            if (response.isSuccess()) {
                                VersionResult result = response.getResult();
                                VersionResult.DataBean bean = result.getData();
                                String ver = PackageUtil.INSTANCE.getVersionName();
                                if (!bean.getVersion().equals(ver)) {
                                    view.updateVer(bean.getUrl());
                                } else {
                                    view.toActivity();
                                }
                            } else {
                                view.toActivity();
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
