package com.ladsoft.bakingapp.di.module;

import com.ladsoft.bakingapp.data.network.api.BakingAppApiModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class ApiModule {

    @Singleton @Provides
    static BakingAppApiModule providesApi() {
        return new BakingAppApiModule();
    }

}
