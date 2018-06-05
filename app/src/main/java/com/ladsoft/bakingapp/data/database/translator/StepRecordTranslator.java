package com.ladsoft.bakingapp.data.database.translator;

import com.ladsoft.bakingapp.data.database.entity.StepRecord;
import com.ladsoft.bakingapp.entity.Step;
import com.ladsoft.bakingapp.translator.Translator;

public class StepRecordTranslator implements Translator<Step, StepRecord> {
    @Override
    public StepRecord translate(Step source) {
        return new StepRecord(source.getId(),
                        source.getRecipeId(),
                        source.getShortDescription() != null ? source.getShortDescription() : "",
                        source.getDescription() != null ? source.getDescription() : "",
                        source.getVideoUrl() != null ? source.getVideoUrl() : "",
                        source.getThumbnailUrl() != null ? source.getThumbnailUrl() : ""
                    );
    }
}
