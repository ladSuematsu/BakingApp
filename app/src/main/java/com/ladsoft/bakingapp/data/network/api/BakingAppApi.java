package com.ladsoft.bakingapp.data.network.api;

import com.ladsoft.bakingapp.data.network.entity.RecipePayload;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingAppApi {
    @GET("/android-baking-app-json")
    Call<List<RecipePayload>> recipes();
}
