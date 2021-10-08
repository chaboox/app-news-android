package com.achour.newsapp.data.retrofit.interfaces;

import com.google.gson.JsonObject;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsInterface {

    @GET("top-headlines")
    Single<JsonObject> getNews(@Query("sources") String sources, @Query("apiKey") String apiKey);

}
