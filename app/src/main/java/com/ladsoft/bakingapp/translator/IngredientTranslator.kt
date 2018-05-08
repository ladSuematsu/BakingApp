package com.ladsoft.bakingapp.translator


import com.ladsoft.bakingapp.data.network.entity.IngredientPayload
import com.ladsoft.bakingapp.entity.Ingredient

class IngredientTranslator(private val recipeId: Long) : Translator<IngredientPayload, Ingredient> {

    override fun translate(source: IngredientPayload): Ingredient {
        return Ingredient(source.id, recipeId, source.quantity, source.measure ?: "", source.description ?: "")
    }
}
