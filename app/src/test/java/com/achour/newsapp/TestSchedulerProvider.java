package com.achour.newsapp;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

public class TestSchedulerProvider implements SchedulerProvider {

    private final TestScheduler testScheduler;

    public TestSchedulerProvider(final TestScheduler testScheduler) {
        this.testScheduler = testScheduler;
    }

    @Override
    public Scheduler ui() {
        return testScheduler;
    }

    @Override
    public Scheduler computation() {
        return testScheduler;
    }

    @Override
    public Scheduler io() {
        return testScheduler;
    }

    @Override
    public Scheduler special() {
        return null;
    }
}
