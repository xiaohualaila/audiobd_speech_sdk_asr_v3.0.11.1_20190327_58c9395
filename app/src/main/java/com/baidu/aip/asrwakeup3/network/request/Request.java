package com.baidu.aip.asrwakeup3.network.request;


import com.baidu.aip.asrwakeup3.bean.FaceCheckBean;
import com.baidu.aip.asrwakeup3.bean.YUBAIBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Zaifeng on 2018/2/28.
 * 封装请求的接口
 */

public interface Request {


    String YUYIN_URL = "https://api.zq-ai.com/zqcloudapi/v1.0/";//羽白语音


    @GET("query")
    Observable<YUBAIBean> getYUBAIData(@Query("type") String type, @Query("data") String data);

    @POST("face")
    @Multipart
    Call<FaceCheckBean> uploadPicFile(@Part  List<MultipartBody.Part> files);


}
