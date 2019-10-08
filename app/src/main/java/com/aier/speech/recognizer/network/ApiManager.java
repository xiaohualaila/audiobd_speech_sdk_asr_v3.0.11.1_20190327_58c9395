package com.aier.speech.recognizer.network;



import com.aier.speech.recognizer.network.api.CheckFaceApi;
import com.aier.speech.recognizer.network.api.CommonApi;
import com.aier.speech.recognizer.network.api.MapSearchApi;
import com.aier.speech.recognizer.network.api.RedTimeApi;
import com.aier.speech.recognizer.network.api.YubaiApi;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 * 集中处理Api相关配置的Manager类
 */
public class ApiManager {

    private CommonApi commonApi;
    private YubaiApi yubaiApi;
    private CheckFaceApi checkFaceApi;
    private RedTimeApi redTimeApi;
    private MapSearchApi mapSearchApi;
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
    /**
     * 红军知识问答 api
     * @return
     */
    public RedTimeApi getAnswerQuestionService() {
        if (redTimeApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASIC_ANSWERQUESTION_URL)
                    .client(mClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            redTimeApi =  retrofit.create(RedTimeApi.class);
        }
        return redTimeApi;
    }

    /**
     * 红军地图查询
     * @return
     */
    public MapSearchApi getMapSearchService() {
        if (mapSearchApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASIC_MAP_SEARCH_URL)
                    .client(mClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mapSearchApi =  retrofit.create(MapSearchApi.class);
        }
        return mapSearchApi;
    }

    /**
     * 红色人物图谱api
     * @return
     */
    public CheckFaceApi getRedPeoPlePicService() {
        if (checkFaceApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASIC_RED_PEOPLE_PIC_URL)
                    .client(mClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            checkFaceApi =  retrofit.create(CheckFaceApi.class);
        }
        return checkFaceApi;
    }
}
