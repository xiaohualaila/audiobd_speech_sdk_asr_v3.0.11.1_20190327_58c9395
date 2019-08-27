package com.aier.speech.recognizer.network.api;

import com.aier.speech.recognizer.bean.VersionResult;
import com.aier.speech.recognizer.network.response.Response;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CommonApi {

    @POST("nkcleartext")
    Observable<Response<VersionResult>> getVerDataForBody(@Body RequestBody body);
}
