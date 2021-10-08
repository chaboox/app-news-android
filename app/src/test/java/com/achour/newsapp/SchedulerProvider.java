package com.achour.newsapp;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();

    Scheduler special();


}
