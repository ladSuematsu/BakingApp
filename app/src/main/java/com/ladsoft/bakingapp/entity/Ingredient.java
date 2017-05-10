package com.ladsoft.bakingapp.entity;


public class Ingredient {
    private long id;
    private long recipeId;
    private int quantity;
    private String measure;
    private String description;

    public Ingredient(long id, long recipeId, int quantity, String measure, String description) {
        this.id = id;
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.measure = measure;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getDescription() {
        return description;
    }
}
