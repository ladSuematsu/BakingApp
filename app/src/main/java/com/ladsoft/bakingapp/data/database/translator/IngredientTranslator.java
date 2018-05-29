package com.ladsoft.bakingapp.data.database.translator;

import com.ladsoft.bakingapp.data.database.entity.IngredientRecord;
import com.ladsoft.bakingapp.entity.Ingredient;
import com.ladsoft.bakingapp.translator.Translator;

public class IngredientTranslator implements Translator<IngredientRecord, Ingredient>{
    @Override
    public Ingredient translate(IngredientRecord source) {
        return new Ingredient(source.getId(),
                            source.getRecipeId(),
                            source.getQuantity(),
                            source.getMeasure() != null ? source.getMeasure() : "",
                            source.getDescription() != null ? source.getDescription() : "");
    }
}
