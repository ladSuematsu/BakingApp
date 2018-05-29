package com.ladsoft.bakingapp.data.network.api;

import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.ladsoft.bakingapp.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BakingAppApiModule {
    private static final String API_BASE_URL = BuildConfig.API_BASE_URL;

    private Gson gson;
    private HttpLoggingInterceptor loggingInterceptor;
    private OkHttpClient okHttpClient;
    private BakingAppApi api;

    @VisibleForTesting
    IdlingResource idlingResource;

    BakingAppApiModule() {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();

        loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC);
        okHttpClient = new OkHttpClient.Builder()
                            .addInterceptor(loggingInterceptor)
                            .build();

        idlingResource = OkHttp3IdlingResource.create(BakingAppApiModule.class.getSimpleName(), okHttpClient);

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        api = restAdapter.create(BakingAppApi.class);
    }

    public BakingAppApi getApi() {
        return api;
    }
}
