package com.ladsoft.bakingapp.data.database.translator

import com.ladsoft.bakingapp.data.database.entity.StepRecord
import com.ladsoft.bakingapp.entity.Step
import com.ladsoft.bakingapp.translator.Translator

class StepRecordTranslator: Translator<Step, StepRecord> {
    override fun translate(source: Step): StepRecord{
        return StepRecord(source.id, source.recipeId, source.stepId, source.shortDescription, source.description, source.videoUrl, source.thumbnailUrl)
    }
}