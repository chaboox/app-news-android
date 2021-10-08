package com.achour.newsapp.data.retrofit.clients;

import com.achour.newsapp.BuildConfig;
import com.achour.newsapp.data.retrofit.interfaces.NewsInterface;
import com.achour.newsapp.utils.ConstantsUtils;
import com.google.gson.JsonObject;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsClient {
    private static final String BASE_URL = ConstantsUtils.API_URL;
    private final NewsInterface mInterface;
    private static NewsClient instance;

    private NewsClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mInterface = retrofit.create(NewsInterface.class);
    }

    public static NewsClient getInstance() {
        return (instance == null) ? new NewsClient() : instance;
    }

    public Single<JsonObject> getNews() {
        return mInterface.getNews(BuildConfig.SOURCE_ID, BuildConfig.API_KEY);
    }
}
