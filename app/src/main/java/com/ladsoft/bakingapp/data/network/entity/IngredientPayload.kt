package com.ladsoft.bakingapp.data.network.entity


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IngredientPayload(
    var id: Long,

    @Expose
    @SerializedName("quantity")
    val quantity: Double,

    @Expose
    @SerializedName("measure")
    val measure: String?,

    @Expose
    @SerializedName("ingredient")
    val description: String?
)
