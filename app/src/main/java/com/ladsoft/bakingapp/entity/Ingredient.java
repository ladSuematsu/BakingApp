package com.ladsoft.bakingapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable{
    private long id;
    private long recipeId;
    private double quantity;
    private String measure;
    private String description;

    public Ingredient(long id, long recipeId, double quantity, String measure, String description) {
        this.id = id;
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.measure = measure;
        this.description = description;
    }

    protected Ingredient(Parcel in) {
        id = in.readLong();
        recipeId = in.readLong();
        quantity = in.readDouble();
        measure = in.readString();
        description = in.readString();
    }

    public long getId() {
        return id;
    }

    public long getRecipeId() {
        return recipeId;
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

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(recipeId);
        parcel.writeDouble(quantity);
        parcel.writeString(measure);
        parcel.writeString(description);
    }
}
