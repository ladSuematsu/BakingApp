package com.ladsoft.bakingapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ladsoft.bakingapp.data.database.entity.StepRecord;

import java.util.List;

@Dao
public interface StepDao {
    String QUERY_SELECT_ALL =
                    "SELECT * FROM " + StepRecord.TABLE_NAME;

    String QUERY_SELECT_BY_RECIPE_ID =
                            "SELECT * FROM " + StepRecord.TABLE_NAME
                            + " WHERE " + StepRecord.RECIPE_ID_COLUMN_NAME + " = :recipeId"
                            + " ORDER BY " + StepRecord.ID_COLUMN_NAME + " ASC";

    String QUERY_DELETE_ALL_BY_RECIPE_ID =
                        "DELETE FROM " + StepRecord.TABLE_NAME
                        + " WHERE " + StepRecord.RECIPE_ID_COLUMN_NAME + " = :recipeId";

    @Query(QUERY_SELECT_ALL)
    List<StepRecord> getAll();

    @Query(QUERY_SELECT_BY_RECIPE_ID)
    List<StepRecord> getByRecipeId(long recipeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(StepRecord step);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(List<StepRecord> step);

    @Query(QUERY_DELETE_ALL_BY_RECIPE_ID)
    void deleteAllByRecipeId(long recipeId);
}
