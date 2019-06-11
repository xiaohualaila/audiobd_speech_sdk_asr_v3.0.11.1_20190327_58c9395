package com.baidu.aip.asrwakeup3.mvp.presenter;


import android.text.TextUtils;
import android.util.Log;

import com.baidu.aip.asrwakeup3.bean.FaceCheckBean;
import com.baidu.aip.asrwakeup3.bean.YUBAIBean;
import com.baidu.aip.asrwakeup3.mvp.contract.MainContract;
import com.baidu.aip.asrwakeup3.mvp.contract.OpenCVContract;
import com.baidu.aip.asrwakeup3.network.URLConstant;
import com.baidu.aip.asrwakeup3.network.request.Request;
import com.baidu.aip.asrwakeup3.network.schedulers.BaseSchedulerProvider;
import com.baidu.aip.asrwakeup3.network.schedulers.SchedulerProvider;

import java.io.File;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenCVPresenter implements OpenCVContract.Persenter{

    private OpenCVContract.View view;

    private BaseSchedulerProvider schedulerProvider;

    private CompositeDisposable mDisposable;

    public OpenCVPresenter(OpenCVContract.View view,
                           SchedulerProvider schedulerProvider) {
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        mDisposable = new CompositeDisposable();

    }

    public void despose(){
        mDisposable.dispose();
    }


    @Override
    public void upLoadPicFile(String pic_path) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody requestBody = null;
        if (!TextUtils.isEmpty(pic_path)) {
            File v_file = new File(pic_path);
            if (v_file.exists()) {
                requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), v_file);
                builder.addFormDataPart("file", v_file.getName(), requestBody);
            }
        }

        List<MultipartBody.Part> parts = builder.build().parts();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URLConstant.UPLOAD_PIC)
                .build();
        Request service = retrofit.create(Request.class);
        Call<FaceCheckBean> call = service.uploadPicFile(parts);
        call.enqueue(new Callback<FaceCheckBean>() {
            @Override
            public void onResponse(Call<FaceCheckBean> call, Response<FaceCheckBean> response) {
                // 已经转换为想要的类型了
                try {
                    FaceCheckBean bean = response.body();
                    Log.i("xxx",bean.toString());
                    view.getDataSuccess(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<FaceCheckBean> call, Throwable t) {
                view.getDataFail();
            }
        });
    }
}
