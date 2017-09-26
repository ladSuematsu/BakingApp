package com.ladsoft.bakingapp.data.database.translator

import com.ladsoft.bakingapp.data.database.entity.RecipeRecord
import com.ladsoft.bakingapp.entity.Recipe
import com.ladsoft.bakingapp.translator.Translator

class RecipeRecordTranslator: Translator<Recipe, RecipeRecord> {
    override fun translate(source: Recipe): RecipeRecord {
        return RecipeRecord(source.id, source.name, source.servings, source.imageUrl)
    }
}