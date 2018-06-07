package com.ladsoft.bakingapp.translator;

import com.ladsoft.bakingapp.data.network.entity.StepPayload;
import com.ladsoft.bakingapp.entity.Step;

public class StepTranslator implements Translator<StepPayload, Step> {
    private final long recipeId;

    public StepTranslator(long recipeId) {
        this.recipeId = recipeId;
    }


    @Override
    public Step translate(StepPayload source) {
        return new Step(source.getId(),
                        recipeId,
                        source.getShortDescription() != null ? source.getShortDescription() : "",
                        source.getDescription() != null ? source.getDescription() : "",
                        source.getVideoUrl() != null ? source.getVideoUrl() : "",
                        source.getThumbnailUrl() != null ? source.getThumbnailUrl() : ""
                    );
    }
}
