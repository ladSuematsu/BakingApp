package com.ladsoft.bakingapp.data.network.api


import android.support.annotation.VisibleForTesting
import android.support.test.espresso.IdlingResource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.ladsoft.bakingapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.TestOnly
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BakingAppApiModule {
    private val API_BASE_URL = BuildConfig.API_BASE_URL

    val gson: Gson
    val loggingInterceptor: HttpLoggingInterceptor
    val okHttpClient: OkHttpClient
    val api: BakingAppApi

    @VisibleForTesting val idlingResource: IdlingResource

    init {
        gson = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create()

        loggingInterceptor = HttpLoggingInterceptor()

        loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        idlingResource = OkHttp3IdlingResource.create(BakingAppApiModule.javaClass.simpleName, okHttpClient)

        val restAdapter = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()


        api = restAdapter.create(BakingAppApi::class.java)
    }
}
