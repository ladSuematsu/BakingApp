package com.ladsoft.bakingapp.data.database.repository

import com.ladsoft.bakingapp.data.database.AppDatabase
import com.ladsoft.bakingapp.entity.Recipe

class DatabaseRecipeRepository(val appDatabase: AppDatabase) {
    fun loadRecipes(): List<Recipe> {
        return listOf()
    }

    fun insertRecipes(recipes: List<Recipe>) {
    }
}