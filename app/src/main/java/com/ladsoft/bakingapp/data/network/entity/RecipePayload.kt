package com.ladsoft.bakingapp.data.network.entity


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RecipePayload (
    @Expose
    @SerializedName("id")
    val id: Long,

    @Expose
    @SerializedName("name")
    val name: String?,

    @Expose
    @SerializedName("servings")
    val servings: Int,

    @Expose
    @SerializedName("image")
    val imageUrl: String?,

    @Expose
    @SerializedName("ingredients")
    val ingredients: List<IngredientPayload>?,

    @Expose
    @SerializedName("steps")
    val steps: List<StepPayload>?
)