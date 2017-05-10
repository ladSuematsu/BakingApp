package com.ladsoft.bakingapp.translator;


import com.ladsoft.bakingapp.data.network.entity.IngredientPayload;
import com.ladsoft.bakingapp.data.network.entity.RecipePayload;
import com.ladsoft.bakingapp.entity.Ingredient;
import com.ladsoft.bakingapp.entity.Recipe;

public class RecipeTranslator implements Translator<RecipePayload, Recipe> {
    @Override
    public Recipe translate(RecipePayload source) {
        return new Recipe(source.getId(), source.getName(), source.getServings(), source.getImageUrl());
    }
}
