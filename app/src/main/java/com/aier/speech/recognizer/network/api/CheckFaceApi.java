package com.aier.speech.recognizer.network.api;

import com.aier.speech.recognizer.bean.FaceCheckBean;
import com.aier.speech.recognizer.bean.SimilarFaceResult;

import java.util.List;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CheckFaceApi {
    @POST("face")
    @Multipart
    Observable<FaceCheckBean> uploadPicFile(@Part List<MultipartBody.Part> files);


    @POST("redface")
    @Multipart
    Observable<SimilarFaceResult> uploadRedPeoplePicFile(@Part List<MultipartBody.Part> files);
}
