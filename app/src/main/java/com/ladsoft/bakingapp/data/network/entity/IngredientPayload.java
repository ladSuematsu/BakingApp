package com.ladsoft.bakingapp.data.network.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IngredientPayload {

    @Expose @SerializedName("id")
    private long id;

    @Expose @SerializedName("quantity")
    private double quantity;

    @Expose @SerializedName("measure")
    private String measure;

    @Expose @SerializedName("ingredient")
    private String description;

    public long getId() {
        return id;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getDescription() {
        return description;
    }
}
