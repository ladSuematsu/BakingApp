package com.ladsoft.bakingapp.data.network.api


import com.google.gson.GsonBuilder
import com.ladsoft.bakingapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BakingAppApiModule {

    private val API_BASE_URL = BuildConfig.API_BASE_URL

    fun provideApi(): BakingAppApi {
        val gson = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        val restAdapter = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return restAdapter.create(BakingAppApi::class.java)
    }

//    companion object {
//        val instance = BakingAppApiModule()
//    }

}
