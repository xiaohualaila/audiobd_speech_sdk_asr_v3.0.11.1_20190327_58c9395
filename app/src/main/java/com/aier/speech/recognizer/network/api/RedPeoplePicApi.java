package com.aier.speech.recognizer.network.api;

import com.aier.speech.recognizer.bean.MapDataResult;
import com.aier.speech.recognizer.bean.MapSearchResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RedPeoplePicApi {
    @GET("redkg")
    Observable<MapSearchResult> getRedPeoplePic(@Query("name") String name);
}
