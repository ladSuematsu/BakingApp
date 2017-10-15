package com.ladsoft.bakingapp.data.database.repository

import com.ladsoft.bakingapp.data.database.AppDatabase
import com.ladsoft.bakingapp.data.database.translator.IngredientRecordTranslator
import com.ladsoft.bakingapp.data.database.translator.IngredientTranslator
import com.ladsoft.bakingapp.entity.Ingredient

class DatabaseIngredientRepository(val appDatabase: AppDatabase) {
    fun loadForRecipeId(recipeId : Long): List<Ingredient> {
        val dao = appDatabase.ingredientDao()

        val ingredientRecords = dao.getByRecipeId(recipeId)

        val translator = IngredientTranslator()
        val recipes = ingredientRecords.map({
            translator.translate(it)
        })

        return recipes
    }

    fun insert(ingredients: List<Ingredient>) {
        val dao = appDatabase.ingredientDao()

        val translator = IngredientRecordTranslator()
        val records = ingredients.map({
            translator.translate(it)
        })

        dao.add(records)
    }

    fun deleteAllByRecipeId(recipeId: Long) {
        val dao = appDatabase.ingredientDao()

        dao.deleteAllByRecipeId(recipeId)
    }
}