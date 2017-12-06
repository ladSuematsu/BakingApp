package com.ladsoft.bakingapp.data.repository


import com.ladsoft.bakingapp.data.network.api.BakingAppApiModule
import com.ladsoft.bakingapp.entity.Recipe
import com.ladsoft.bakingapp.translator.RecipeTranslator

class RecipeRepository {

    fun recipes(): List<Recipe> {
        val response = BakingAppApiModule.api
                            .recipes()
                            .execute()

        var recipes: List<Recipe> = emptyList()
        if (!response.isSuccessful) {
            throw Exception("Request unsuccessful. HTTP error ${response.code()}")
        } else {
            val recipePayloads = response.body()

            val translator = RecipeTranslator()
            if (recipePayloads != null) {
                recipes = recipePayloads.map {
                    translator.translate(it)
                }
            }
        }

        return recipes
    }
}
