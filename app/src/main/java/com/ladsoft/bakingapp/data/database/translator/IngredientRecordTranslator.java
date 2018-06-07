package com.ladsoft.bakingapp.data.database.translator;

import com.ladsoft.bakingapp.data.database.entity.IngredientRecord;
import com.ladsoft.bakingapp.entity.Ingredient;
import com.ladsoft.bakingapp.translator.Translator;

public class IngredientRecordTranslator implements Translator<Ingredient, IngredientRecord>{
    @Override
    public IngredientRecord translate(Ingredient source) {
        return new IngredientRecord(source.getId(),
                            source.getRecipeId(),
                            source.getQuantity(),
                            source.getMeasure() != null ? source.getMeasure() : "",
                            source.getDescription() != null ? source.getDescription() : "");
    }
}
