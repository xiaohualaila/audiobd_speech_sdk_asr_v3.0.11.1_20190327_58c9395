package com.aier.speech.recognizer.network.api;

import com.aier.speech.recognizer.bean.AnswerQuestionResult;
import com.aier.speech.recognizer.bean.MapDataResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RedTimeApi {

    @GET("getQuestionList")
    Observable<AnswerQuestionResult> getAnswerQuestion(@Query("key") String key, @Query("num") int num);


    @GET("getPartyList")
    Observable<MapDataResult> getDangzhibuMapMarker(@Query("channel_type") String channel_type);

}
