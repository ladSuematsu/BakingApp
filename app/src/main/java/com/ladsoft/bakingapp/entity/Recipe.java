package com.ladsoft.bakingapp.entity;


public class Recipe {
    private long id;
    private String name;
    private int servings;
    private String imageUrl;

    public Recipe(long id, String name, int servings, String imageUrl) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.imageUrl = imageUrl;
    }

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
}
