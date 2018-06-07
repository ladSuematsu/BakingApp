package com.ladsoft.bakingapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ladsoft.bakingapp.data.database.entity.RecipeRecord;

import java.util.List;

@Dao
public interface RecipeDao {
    String QUERY_SELECT_ALL =
            "SELECT * FROM " + RecipeRecord.TABLE_NAME;

    String QUERY_SELECT_BY_ID =
        "SELECT * FROM " + RecipeRecord.TABLE_NAME
        + " WHERE " + RecipeRecord.ID_COLUMN_NAME + " = :id"
        + " LIMIT 1";

    String QUERY_DELETE_ALL =
            "DELETE FROM " + RecipeRecord.TABLE_NAME;

    String QUERY_DELETE_NOT_IN =
            "DELETE FROM " + RecipeRecord.TABLE_NAME
            + " WHERE " + RecipeRecord.ID_COLUMN_NAME + " NOT IN (:preserveIds)";

    @Query(QUERY_SELECT_ALL)
    List<RecipeRecord> getAll();

    @Query(QUERY_SELECT_BY_ID)
    RecipeRecord getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(RecipeRecord recipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(List<RecipeRecord> recipes);

    @Query(QUERY_DELETE_ALL)
    void deleteAll();

    @Query(QUERY_DELETE_NOT_IN)
    void deletePreserving(List<Long> preserveIds);
}
