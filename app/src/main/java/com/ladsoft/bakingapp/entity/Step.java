package com.ladsoft.bakingapp.entity;


public class Step {
    private long id;
    private long stepId;
    private long recipeId;
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;

    public Step(long id, long stepId, long recipeId, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.stepId = stepId;
        this.recipeId = recipeId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public long getId() {
        return id;
    }

    public long getStepId() {
        return stepId;
    }

    public long getRecipeId() {
        return recipeId;
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
