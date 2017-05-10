package com.ladsoft.bakingapp.data.network.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StepPayload {

    @Expose @SerializedName("id")
    private long id;

    @Expose @SerializedName("shortDescription")
    private String shortDescription;

    @Expose @SerializedName("description")
    private String description;

    @Expose @SerializedName("videoURL")
    private String videoUrl;

    @Expose @SerializedName("thumbnailURL")
    private String thumbnailUrl;

    public long getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
