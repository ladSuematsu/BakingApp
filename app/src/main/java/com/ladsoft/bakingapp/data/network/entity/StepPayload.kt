package com.ladsoft.bakingapp.data.network.entity


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StepPayload (
    @Expose
    @SerializedName("id")
    val id: Long,

    @Expose
    @SerializedName("shortDescription")
    val shortDescription: String?,

    @Expose
    @SerializedName("description")
    val description: String?,

    @Expose
    @SerializedName("videoURL")
    val videoUrl: String?,

    @Expose
    @SerializedName("thumbnailURL")
    val thumbnailUrl: String?
)
