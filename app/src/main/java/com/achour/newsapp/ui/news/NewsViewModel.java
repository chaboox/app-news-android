package com.achour.newsapp.ui.news;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.achour.newsapp.data.models.NewsModel;
import com.achour.newsapp.data.retrofit.BackendSource;
import com.achour.newsapp.ui.listeners.RetrieveListener;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsViewModel extends ViewModel {

    private static final String TAG = "NewsViewModel";
    private  BackendSource repository = new BackendSource();
    public RetrieveListener retrieveListener;
    public MutableLiveData<List<NewsModel>> listMLD = new MutableLiveData<>();
    private final CompositeDisposable composite = new CompositeDisposable();



    public void getNews() {
        retrieveListener.onStartedRetrieve();
        Single<List<NewsModel>> single = repository.getNews();
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<NewsModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        composite.add(d);
                    }

                    @Override
                    public void onSuccess(List<NewsModel> listNews) {
                        listMLD.setValue(listNews);
                        retrieveListener.onSuccessRetrieve();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        retrieveListener.onFailureRetrieve(e.getMessage());
                    }
                });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onCleared: about to dispose");
        composite.dispose();
    }


}
