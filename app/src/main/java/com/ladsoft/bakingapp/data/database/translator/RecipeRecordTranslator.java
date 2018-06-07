package com.ladsoft.bakingapp.data.database.translator;

import com.ladsoft.bakingapp.data.database.entity.RecipeRecord;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.translator.Translator;

public class RecipeRecordTranslator implements Translator<Recipe, RecipeRecord> {
    @Override
    public RecipeRecord translate(Recipe source) {
        return new RecipeRecord(source.getId(),
                        source.getName() != null ? source.getName() : "",
                        source.getServings(),
                        source.getImageUrl() != null ? source.getImageUrl() : "");
    }
}
