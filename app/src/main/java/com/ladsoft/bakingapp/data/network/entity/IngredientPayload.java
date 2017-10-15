package com.ladsoft.bakingapp.data.network.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IngredientPayload {
    private long id;

    @Expose @SerializedName("quantity")
    private double quantity;

    @Expose @SerializedName("measure")
    private String measure;

    @Expose @SerializedName("ingredient")
    private String description;

    public double getQuantity() {
        return quantity;
    }

    public long getId() {
        return id;
    }

    public String getMeasure() {
        return measure;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }
}
