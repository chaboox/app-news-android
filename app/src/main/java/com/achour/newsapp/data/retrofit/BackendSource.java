package com.achour.newsapp.data.retrofit;

import com.achour.newsapp.data.models.NewsModel;
import com.achour.newsapp.data.retrofit.clients.NewsClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BackendSource {

    private static final String TAG = "BackendSource";


    public Single<List<NewsModel>> getNews() {
        return Single.create(emitter -> {
            Single<JsonObject> single = NewsClient.getInstance().getNews();
            single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<JsonObject>() {
                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onSuccess(JsonObject response) {
                    String status = response.get("status").getAsString();
                    JsonArray articles = response.getAsJsonArray("articles");

                    List<NewsModel> list = new ArrayList<>();

                    if (status.equals("ok")) {
                        Type listType = new TypeToken<List<NewsModel>>() {
                        }.getType();
                        list = new Gson().fromJson(articles, listType);
                    }

                    emitter.onSuccess(list);
                }

                @Override
                public void onError(Throwable e) {
                    emitter.onError(e);
                }
            });
        });
    }

}
