package com.aier.speech.recognizer.network.api;

import com.aier.speech.recognizer.bean.AllMapResult;
import com.aier.speech.recognizer.bean.EventResult;
import com.aier.speech.recognizer.bean.JingdianResult;
import com.aier.speech.recognizer.bean.MapSearchResult;
import com.aier.speech.recognizer.bean.RenWuResult;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface MapSearchApi {


    @POST("search")
    Observable<MapSearchResult> getMapSearch(@Query("keywork") String key,@Query("channel_type") String channel_type);

    //景点
    @POST("getNewsIntr")
    Observable<JingdianResult> getJingDianMapSearch(@Query("name") String name, @Query("channel_type") String channel_type);

    //人物
    @POST("getNewsByFigure")
    Observable<RenWuResult> getRenWuMapSearch(@Query("name") String name, @Query("channel_type") String channel_type);

    //事件
    @POST("getNewsByEvent")
    Observable<EventResult> getEventMapSearch(@Query("name") String name, @Query("channel_type") String channel_type);


    //获取所有地图标记
    @POST("getPoint")
    Observable<AllMapResult> getAllMapSearch(@Query("tab") String tab);

    //获取所有地图标记
    @POST("getPoint")
    Observable<AllMapResult> getAllMapSearch(@Query("tab") String tab,@Query("name") String name);

}
