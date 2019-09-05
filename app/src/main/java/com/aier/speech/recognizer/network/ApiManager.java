package com.aier.speech.recognizer.network;

import com.aier.speech.recognizer.network.api.CheckFaceApi;
import com.aier.speech.recognizer.network.api.CommonApi;
import com.aier.speech.recognizer.network.api.YubaiApi;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiManager {

    private CommonApi commonApi;
    private YubaiApi yubaiApi;
    private CheckFaceApi checkFaceApi;

    private static ApiManager sApiManager;

    private static OkHttpClient mClient;

    private ApiManager() {

    }

    public static ApiManager getInstence() {
        if (sApiManager == null) {
            synchronized (ApiManager.class) {
                if (sApiManager == null) {
                    sApiManager = new ApiManager();
                }
            }
        }
        mClient = new OkHttpClient.Builder()
                .addInterceptor(new CustomInterceptor())
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        return sApiManager;
    }


    /**
     * 封装 羽白 API
     */
    public YubaiApi getYubaiService() {
        if (yubaiApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.YUBAI_API_URL)
                    .client(mClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            yubaiApi = retrofit.create(YubaiApi.class);
        }
        return yubaiApi;
    }


    /**
     * 封装 通用API
     */
    public CommonApi getCommonService() {
        if (commonApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASIC_API_URL)
                    .client(mClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            commonApi = retrofit.create(CommonApi.class);
        }
        return commonApi;
    }

    /**
     * 人脸识别 api
     * @return
     */
    public CheckFaceApi getCheckFaceService() {
        if (checkFaceApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.UPLOAD_PIC_API_URL)
                    .client(mClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            checkFaceApi =  retrofit.create(CheckFaceApi.class);
        }
        return checkFaceApi;
    }
}
