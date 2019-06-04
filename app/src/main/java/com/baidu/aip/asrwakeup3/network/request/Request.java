package com.baidu.aip.asrwakeup3.network.request;


import com.baidu.aip.asrwakeup3.bean.YUBAIBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Zaifeng on 2018/2/28.
 * 封装请求的接口
 */

public interface Request {

    String HOST = "https://cloud.zq12369.com/nodeapi/";
    String YUYIN_URL = "https://api.zq-ai.com/zqcloudapi/v1.0/";//羽白语音

    @GET("query")
    Call<YUBAIBean> getWeatherData(@Query("type") String type, @Query("data") String data);





}
