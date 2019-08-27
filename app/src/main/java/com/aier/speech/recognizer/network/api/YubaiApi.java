package com.aier.speech.recognizer.network.api;

import io.reactivex.Observable;
import com.aier.speech.recognizer.bean.YUBAIBean;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface YubaiApi {

    @GET("query")
    Observable<YUBAIBean> getYUBAIData(@Query("type") String type, @Query("data") String data);


}
