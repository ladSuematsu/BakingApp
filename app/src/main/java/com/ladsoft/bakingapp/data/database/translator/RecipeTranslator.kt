package com.ladsoft.bakingapp.data.database.translator

import com.ladsoft.bakingapp.data.database.entity.RecipeRecord
import com.ladsoft.bakingapp.entity.Recipe
import com.ladsoft.bakingapp.translator.Translator

class RecipeTranslator: Translator<RecipeRecord, Recipe> {
    override fun translate(source: RecipeRecord): Recipe {
        return Recipe(source.id, source.name, source.servings, source.imageUrl, listOf(), listOf())
    }
}