package com.achour.newsapp.ui.listeners;

public interface RetrieveListener {

    void onStartedRetrieve();

    void onSuccessRetrieve();

    void onFailureRetrieve(String message);

}
