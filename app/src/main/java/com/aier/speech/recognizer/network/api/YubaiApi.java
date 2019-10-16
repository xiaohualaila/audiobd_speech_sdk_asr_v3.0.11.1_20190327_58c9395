package com.aier.speech.recognizer.network.api;

import io.reactivex.Observable;
import com.aier.speech.recognizer.bean.YUBAIBean;
import com.aier.speech.recognizer.bean.YUBAIBean2;

import retrofit2.http.GET;
import retrofit2.http.Query;


public interface YubaiApi {

//    @GET("query")
//    Observable<YUBAIBean> getYUBAIData(@Query("type") String type, @Query("data") String data);
    @GET("redkgqa")
    Observable<YUBAIBean2> getYUBAIData(@Query("data") String data);

}
