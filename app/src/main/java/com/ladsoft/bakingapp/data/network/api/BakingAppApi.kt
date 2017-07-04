package com.ladsoft.bakingapp.data.network.api


import com.ladsoft.bakingapp.data.network.entity.RecipePayload
import retrofit2.Call
import retrofit2.http.GET

interface BakingAppApi {

    @GET("/android-baking-app-json")
    fun recipes(): Call<List<RecipePayload>>
}
