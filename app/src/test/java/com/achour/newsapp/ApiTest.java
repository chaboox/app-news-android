package com.achour.newsapp;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.achour.newsapp.data.models.NewsModel;
import com.achour.newsapp.data.retrofit.BackendSource;
import com.achour.newsapp.data.retrofit.clients.NewsClient;
import com.achour.newsapp.data.retrofit.interfaces.NewsInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;
import kotlin.jvm.JvmField;
import kotlin.text.Charsets;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;

import org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(JUnit4.class)
public class ApiTest {

    @Rule
    @JvmField
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    public NewsInterface service;
    public MockWebServer mockWebServer;
    TestSchedulerProvider testSchedulerProvider;
    TestScheduler testScheduler;
    TestObserver<JsonObject> testObserver;

    @Before
    public void createService() {
        testScheduler = new TestScheduler();
        testSchedulerProvider = new TestSchedulerProvider(testScheduler);
        testObserver = new TestObserver<>();
        mockWebServer = new MockWebServer();
        service = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("https://newsapi.org/v2/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(NewsInterface.class);
    }

    @After
    public void stopService() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void getNews() throws InterruptedException, IOException {
        enqueueResponse("response.json");
        Single<JsonObject> single = service.getNews("bbc-news", BuildConfig.API_KEY);
        single.subscribeOn(testSchedulerProvider.io()).observeOn(testSchedulerProvider.ui())
                .test()
                .assertSubscribed()
                .assertNoErrors();
    }


    private void enqueueResponse(String fileName) {
        InputStream inputStream = this.getClass().getClassLoader().
                getResourceAsStream(fileName);

        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        try {
            mockWebServer.enqueue(mockResponse.setBody(source.readString(Charsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
