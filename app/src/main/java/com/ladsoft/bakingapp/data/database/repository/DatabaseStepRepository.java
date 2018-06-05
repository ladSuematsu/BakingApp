package com.ladsoft.bakingapp.data.database.repository;

import com.ladsoft.bakingapp.data.database.AppDatabase;
import com.ladsoft.bakingapp.data.database.dao.StepDao;
import com.ladsoft.bakingapp.data.database.entity.StepRecord;
import com.ladsoft.bakingapp.data.database.translator.StepRecordTranslator;
import com.ladsoft.bakingapp.data.database.translator.StepTranslator;
import com.ladsoft.bakingapp.entity.Step;
import com.ladsoft.bakingapp.translator.Translator;

import java.util.ArrayList;
import java.util.List;

public class DatabaseStepRepository {
    private final StepDao dao;
    private final Translator<StepRecord, Step> translator;
    private final Translator<Step, StepRecord> translatorRecord;

    public DatabaseStepRepository(AppDatabase appDatabase) {
        dao = appDatabase.stepDao();
        translator = new StepTranslator();
        translatorRecord = new StepRecordTranslator();
    }

    public List<Step> loadForRecipeId(long recipeId) {
        List<Step> result = new ArrayList<>();

        List<StepRecord> records = dao.getByRecipeId(recipeId);
        if (!records.isEmpty()) {
            for (StepRecord record : records) {
                result.add(translator.translate(record));
            }
        }

        return result;
    }

    public void insert(List<Step> steps) {
        List<StepRecord> records = new ArrayList<>();

        for (Step step : steps) {
            records.add(translatorRecord.translate(step));
        }

        dao.add(records);
    }

    public void deleteAllByRecipeId(long recipeId) {
        dao.deleteAllByRecipeId(recipeId);
    }
}
