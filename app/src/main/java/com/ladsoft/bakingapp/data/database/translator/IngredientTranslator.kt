package com.ladsoft.bakingapp.data.database.translator

import com.ladsoft.bakingapp.data.database.entity.IngredientRecord
import com.ladsoft.bakingapp.entity.Ingredient
import com.ladsoft.bakingapp.translator.Translator

class IngredientTranslator: Translator<IngredientRecord, Ingredient> {
    override fun translate(source: IngredientRecord): Ingredient {
        return Ingredient(source.id, source.recipeId, source.quantity, source.measure, source.description)
    }
}