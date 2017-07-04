package com.ladsoft.bakingapp.data.network.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipePayload {

    public RecipePayload() {
    }

    @Expose @SerializedName("id")
    private long id;

    @Expose @SerializedName("name")
    private String name;

    @Expose @SerializedName("servings")
    private int servings;

    @Expose @SerializedName("image")
    private String imageUrl;

    @Expose @SerializedName("ingredients")
    private List<IngredientPayload> ingredients;

    @Expose @SerializedName("steps")
    private List<StepPayload> steps;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<IngredientPayload> getIngredients() {
        return ingredients;
    }

    public List<StepPayload> getSteps() {
        return steps;
    }
}
