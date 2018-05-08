package com.ladsoft.bakingapp.translator

import com.ladsoft.bakingapp.data.network.entity.RecipePayload
import com.ladsoft.bakingapp.entity.Recipe

class RecipeTranslator : Translator<RecipePayload, Recipe> {
    override fun translate(source: RecipePayload): Recipe {
        val ingredientTranslator = IngredientTranslator(source.id)
        val stepTranslator = StepTranslator(source.id)

        val ingredients = source.ingredients
                ?.sortedWith(compareBy{it.description?.toUpperCase()})
                ?.mapIndexed { index, it ->
                    it.id = index.toLong()
                    ingredientTranslator.translate(it)
                }

        val steps = source.steps?.map { stepTranslator.translate(it) }

        return Recipe(source.id, source.name ?: "", source.servings, source.imageUrl ?: "",
                    ingredients ?: emptyList(), steps ?: emptyList())
    }
}
