package com.ladsoft.bakingapp.data.database.translator

import com.ladsoft.bakingapp.data.database.entity.IngredientRecord
import com.ladsoft.bakingapp.entity.Ingredient
import com.ladsoft.bakingapp.translator.Translator

class IngredientRecordTranslator: Translator<Ingredient, IngredientRecord> {
    override fun translate(source: Ingredient): IngredientRecord {
        return IngredientRecord(source.id, source.recipeId, source.quantity, source.measure, source.description)
    }
}