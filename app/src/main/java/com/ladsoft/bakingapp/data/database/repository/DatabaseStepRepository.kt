package com.ladsoft.bakingapp.data.database.repository

import com.ladsoft.bakingapp.data.database.AppDatabase
import com.ladsoft.bakingapp.data.database.translator.IngredientRecordTranslator
import com.ladsoft.bakingapp.data.database.translator.StepRecordTranslator
import com.ladsoft.bakingapp.data.database.translator.StepTranslator
import com.ladsoft.bakingapp.entity.Step

class DatabaseStepRepository(val appDatabase: AppDatabase) {
    fun loadForRecipeId(recipeId : Long): List<Step> {
        val dao = appDatabase.stepDao()
        val stepRecords = dao.getByRecipeId(recipeId)

        val translator = StepTranslator()
        val steps = stepRecords.map({
            translator.translate(it)
        })

        return steps
    }

    fun insertRecipes(steps: List<Step>) {
        val dao = appDatabase.stepDao()

        val translator = StepRecordTranslator()
        val records = steps.map({
            translator.translate(it)
        })

        dao.add(records)
    }
}