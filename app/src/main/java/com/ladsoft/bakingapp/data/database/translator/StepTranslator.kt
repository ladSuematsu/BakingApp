package com.ladsoft.bakingapp.data.database.translator

import com.ladsoft.bakingapp.data.database.entity.StepRecord
import com.ladsoft.bakingapp.entity.Step
import com.ladsoft.bakingapp.translator.Translator

class StepTranslator: Translator<StepRecord, Step> {
    override fun translate(source: StepRecord): Step{
        return Step(source.id, source.recipeId, source.shortDescription, source.description, source.videoUrl, source.thumbnailUrl)
    }
}