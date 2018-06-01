package com.ladsoft.bakingapp.application;

import android.app.Activity;
import android.app.Application;
import android.app.Service;

import com.ladsoft.bakingapp.di.component.AppComponent;
import com.ladsoft.bakingapp.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;

public class BakingAppApplication extends Application implements HasActivityInjector, HasServiceInjector{

    @Inject DispatchingAndroidInjector<Activity> dispatchingActivityAndroidInjector;
    @Inject DispatchingAndroidInjector<Service> dispatchingServiceAndroidInjector;

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build();

        appComponent.inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityAndroidInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return dispatchingServiceAndroidInjector;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
