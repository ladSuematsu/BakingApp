package com.ladsoft.bakingapp.translator;

import com.ladsoft.bakingapp.data.network.entity.IngredientPayload;
import com.ladsoft.bakingapp.entity.Ingredient;

public class IngredientTranslator implements Translator<IngredientPayload, Ingredient>{
    private final long recipeId;

    public IngredientTranslator(long recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public Ingredient translate(IngredientPayload source) {
        return new Ingredient(source.getId(),
                            recipeId,
                            source.getQuantity(),
                            source.getMeasure() != null ? source.getMeasure() : "",
                            source.getDescription() != null ? source.getDescription() : "");
    }
}
