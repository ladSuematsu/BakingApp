package com.ladsoft.bakingapp.data.network.api;


import com.ladsoft.bakingapp.data.network.entity.IngredientPayload;

import java.util.List;

import retrofit2.http.GET;

public interface BakingAppApi {

    @GET("/topher/2017/May/5907926b_baking/baking")
    List<IngredientPayload> getRecipes();
}
