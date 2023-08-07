package com.sw.beauty.network;

import com.sw.beauty.bean.ModelResponse;
import com.sw.beauty.bean.PicModelResponse;

import io.reactivex.Observable;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestService {
    @GET("model/getAllModels")
    Observable<ModelResponse> getHomeModels();

    @GET("model/getPicModel")
    Observable<PicModelResponse> getPicModelsById(@Query("modelId") String modelId);
}
