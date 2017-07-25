package com.ladsoft.bakingapp.entity;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipe implements Parcelable {
    private long id;
    private String name;
    private int servings;
    private String imageUrl;
    private List<Ingredient> ingredients;
    private List<Step> steps;

    Recipe(){}

    public Recipe(long id, String name, int servings, String imageUrl) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.imageUrl = imageUrl;
    }

    public Recipe(long id, String name, int servings, String imageUrl, List<Ingredient> ingredients, List<Step> steps) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    protected Recipe(Parcel in) {
        id = in.readLong();
        name = in.readString();
        servings = in.readInt();
        imageUrl = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeString(imageUrl);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

}
