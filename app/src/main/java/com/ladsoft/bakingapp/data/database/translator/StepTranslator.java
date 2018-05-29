package com.ladsoft.bakingapp.data.database.translator;

import com.ladsoft.bakingapp.data.database.entity.StepRecord;
import com.ladsoft.bakingapp.entity.Step;
import com.ladsoft.bakingapp.translator.Translator;

public class StepTranslator implements Translator<StepRecord, Step> {
    @Override
    public Step translate(StepRecord source) {
        return new Step(source.getId(),
                        source.getRecipeId(),
                        source.getShortDescription() != null ? source.getShortDescription() : "",
                        source.getDescription() != null ? source.getDescription() : "",
                        source.getVideoUrl() != null ? source.getVideoUrl() : "",
                        source.getThumbnailUrl() != null ? source.getThumbnailUrl() : ""
                    );
    }
}
