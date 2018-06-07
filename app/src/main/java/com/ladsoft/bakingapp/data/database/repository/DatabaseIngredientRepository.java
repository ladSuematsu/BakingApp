package com.ladsoft.bakingapp.data.database.repository;

import com.ladsoft.bakingapp.data.database.AppDatabase;
import com.ladsoft.bakingapp.data.database.dao.IngredientDao;
import com.ladsoft.bakingapp.data.database.entity.IngredientRecord;
import com.ladsoft.bakingapp.data.database.translator.IngredientRecordTranslator;
import com.ladsoft.bakingapp.data.database.translator.IngredientTranslator;
import com.ladsoft.bakingapp.entity.Ingredient;
import com.ladsoft.bakingapp.translator.Translator;

import java.util.ArrayList;
import java.util.List;

public class DatabaseIngredientRepository {
    private final IngredientDao dao;
    private final Translator<IngredientRecord, Ingredient> translator;
    private final Translator<Ingredient, IngredientRecord> recordTranslator;

    public DatabaseIngredientRepository(AppDatabase appDatabase) {
        dao = appDatabase.ingredientDao();
        translator = new IngredientTranslator();
        recordTranslator = new IngredientRecordTranslator();
    }

    public List<Ingredient> loadForRecipeId(long recipeId) {
        List<Ingredient> result = new ArrayList<>();

        List<IngredientRecord> records = dao.getByRecipeId(recipeId);
        if (!records.isEmpty()) {
            for (IngredientRecord record : records) {
                result.add(translator.translate(record));
            }
        }

        return result;
    }

    public void insert(List<Ingredient> ingredients) {
        List<IngredientRecord> records = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            records.add(recordTranslator.translate(ingredient));
        }

        dao.add(records);
    }

    public void deleteAllByRecipeId(long recipeId) {
        dao.deleteAllByRecipeId(recipeId);
    }
}
