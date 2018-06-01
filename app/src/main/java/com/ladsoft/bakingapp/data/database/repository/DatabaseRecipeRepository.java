package com.ladsoft.bakingapp.data.database.repository;

import com.ladsoft.bakingapp.data.database.AppDatabase;
import com.ladsoft.bakingapp.data.database.dao.RecipeDao;
import com.ladsoft.bakingapp.data.database.entity.RecipeRecord;
import com.ladsoft.bakingapp.data.database.translator.RecipeRecordTranslator;
import com.ladsoft.bakingapp.data.database.translator.RecipeTranslator;
import com.ladsoft.bakingapp.entity.Recipe;
import com.ladsoft.bakingapp.translator.Translator;

import java.util.ArrayList;
import java.util.List;

public class DatabaseRecipeRepository {
    private final RecipeDao dao;

    private final Translator<Recipe, RecipeRecord> recipeRecordTranslator;
    private final Translator<RecipeRecord, Recipe> recipeTranslator;

    public DatabaseRecipeRepository(AppDatabase appDatabase) {
        this.dao = appDatabase.recipeDao();
        this.recipeRecordTranslator = new RecipeRecordTranslator();
        this.recipeTranslator = new RecipeTranslator();
    }

    public Recipe loadRecipe(long recipeId)  {
        RecipeRecord record = dao.getById(recipeId);
        return record != null ? recipeTranslator.translate(record) : null;
    }

    public List<Recipe> loadRecipes() {
        List<Recipe> result = new ArrayList<>();

        List<RecipeRecord> records = dao.getAll();
        for (RecipeRecord record : records) {
            result.add(recipeTranslator.translate(record));
        }

        return result;
    }

    public void insert(List<Recipe> recipes) {
        List<RecipeRecord> records = new ArrayList<>();

        for (Recipe recipe : recipes) {
            records.add(recipeRecordTranslator.translate(recipe));
        }

        dao.add(records);
    }

    public void insert(Recipe recipe) {
        RecipeRecord record = recipeRecordTranslator.translate(recipe);
        dao.add(record);
    }

    public void deleteAll() {
        dao.deleteAll();
    }

    public void deletePreserving(List<Long> preserveIds){
        dao.deletePreserving(preserveIds);
    }
}
