package com.aier.speech.recognizer.network.api;

import com.aier.speech.recognizer.bean.AddQuestionResult;
import com.aier.speech.recognizer.bean.AnswerQuestionResult;
import com.aier.speech.recognizer.bean.MapDataResult;
import com.aier.speech.recognizer.bean.QuestionRankResult;
import com.aier.speech.recognizer.bean.UniqidResult;
import java.util.List;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface RedTimeApi {

    @GET("getQuestionList")
    Observable<AnswerQuestionResult> getAnswerQuestion(@Query("key") String key, @Query("num") int num);


    @GET("getPartyList")
    Observable<MapDataResult> getDangzhibuMapMarker(@Query("channel_type") String channel_type);

    @POST("addFaceImage")
    @Multipart
    Observable<UniqidResult> upLoadPicGetUseIdFile(@Query("key") String key,@Part List<MultipartBody.Part> files);

    @GET("addQuestionResult")
    Observable<AddQuestionResult> addQuestionResult(@Query("uniqid") String uniqid, @Query("score") String score);

    @GET("getQuestionRank")
    Observable<QuestionRankResult> getQuestionRank();

}
