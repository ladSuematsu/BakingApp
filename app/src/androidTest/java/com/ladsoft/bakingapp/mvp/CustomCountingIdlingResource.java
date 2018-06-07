package com.ladsoft.bakingapp.mvp;

import android.support.test.espresso.IdlingResource;
import java.util.concurrent.atomic.AtomicInteger;


public class CustomCountingIdlingResource implements IdlingResource {
    private final AtomicInteger counter = new AtomicInteger(0);
    private IdlingResource.ResourceCallback resourceCallback;

    private final String resourceName;

    public CustomCountingIdlingResource(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String getName() {
        return resourceName;
    }

    @Override
    public boolean isIdleNow() {
        return counter.get() == 0;
    }

    @Override
    public void registerIdleTransitionCallback(IdlingResource.ResourceCallback callback) {
        resourceCallback = callback;
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    public void increment() {
        counter.getAndIncrement();
    }

    public void decrement() {
        int counterValue = counter.decrementAndGet();

        if (counterValue == 0) {
            if (resourceCallback != null) {
                resourceCallback.onTransitionToIdle();
            }
        }

        if (counterValue < 0) {
            throw new IllegalArgumentException("Counter has been corrupted");
        }
    }

}