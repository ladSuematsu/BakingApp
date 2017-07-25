package com.ladsoft.bakingapp.translator


import com.ladsoft.bakingapp.data.network.entity.StepPayload
import com.ladsoft.bakingapp.entity.Step

class StepTranslator(private val recipeId: Long) : Translator<StepPayload, Step> {

    override fun translate(source: StepPayload): Step {
        return Step(0, source.id, recipeId, source.shortDescription, source.description,
                source.videoUrl, source.thumbnailUrl)
    }
}
