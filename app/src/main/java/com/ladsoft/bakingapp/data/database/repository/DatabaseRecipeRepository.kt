package com.ladsoft.bakingapp.data.database.repository

import com.ladsoft.bakingapp.data.database.AppDatabase
import com.ladsoft.bakingapp.data.database.translator.RecipeRecordTranslator
import com.ladsoft.bakingapp.data.database.translator.RecipeTranslator
import com.ladsoft.bakingapp.entity.Recipe

class DatabaseRecipeRepository(val appDatabase: AppDatabase) {
    fun loadRecipes(): List<Recipe> {
        val dao = appDatabase.recipeDao()

        val recipeRecords = dao.getAll()

        val translator = RecipeTranslator()
        val recipes = recipeRecords.map({
            translator.translate(it)
        })

        return recipes
    }

    fun insertRecipes(recipes: List<Recipe>) {
        val dao = appDatabase.recipeDao()

        val translator = RecipeRecordTranslator()
        val records = recipes.map({
            translator.translate(it)
        })

        dao.add(records)
    }
}