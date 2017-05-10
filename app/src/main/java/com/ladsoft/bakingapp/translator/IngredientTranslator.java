package com.ladsoft.bakingapp.translator;


import com.ladsoft.bakingapp.data.network.entity.IngredientPayload;
import com.ladsoft.bakingapp.data.network.entity.RecipePayload;
import com.ladsoft.bakingapp.entity.Ingredient;
import com.ladsoft.bakingapp.entity.Recipe;

public class IngredientTranslator implements Translator<IngredientPayload, Ingredient> {

    private long recipeId;

    public IngredientTranslator(long recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public Ingredient translate(IngredientPayload source) {
        return new Ingredient(source.getId(), recipeId, source.getQuantity(), source.getMeasure(), source.getDescription());
    }
}
