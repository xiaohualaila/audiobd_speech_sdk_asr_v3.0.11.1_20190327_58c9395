package com.aier.speech.recognizer.network.api;

import com.aier.speech.recognizer.bean.MapSearchResult;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface MapSearchApi {


    @POST("search")
    Observable<MapSearchResult> getMapSearch(@Query("keywork") String key);

}
