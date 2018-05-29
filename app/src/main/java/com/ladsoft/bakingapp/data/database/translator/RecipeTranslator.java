package com.ladsoft.bakingapp.data.database.translator;

import com.ladsoft.bakingapp.data.database.entity.RecipeRecord;
import com.ladsoft.bakingapp.entity.Ingredient;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.entity.Step;
import com.ladsoft.bakingapp.translator.Translator;

import java.util.ArrayList;

public class RecipeTranslator implements Translator<RecipeRecord, Recipe> {
    @Override
    public Recipe translate(RecipeRecord source) {
        return new Recipe(source.getId(),
                        source.getName() != null ? source.getName() : "",
                        source.getServings(),
                        source.getImageUrl() != null ? source.getImageUrl() : "",
                        new ArrayList<Ingredient>(),
                        new ArrayList<Step>());
    }
}
