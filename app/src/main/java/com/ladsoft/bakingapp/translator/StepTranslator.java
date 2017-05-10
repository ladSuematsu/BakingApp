package com.ladsoft.bakingapp.translator;


import com.ladsoft.bakingapp.data.network.entity.StepPayload;
import com.ladsoft.bakingapp.entity.Step;

public class StepTranslator implements Translator<StepPayload, Step>  {

    private long recipeId;

    public StepTranslator(long recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public Step translate(StepPayload source) {
        return new Step(0, source.getId(), recipeId, source.getShortDescription(), source.getDescription(),
                source.getVideoUrl(), source.getThumbnailUrl());
    }
}
