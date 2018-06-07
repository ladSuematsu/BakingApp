package com.ladsoft.bakingapp.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ladsoft.bakingapp.data.database.entity.IngredientRecord;

import java.util.List;

@Dao
public interface IngredientDao {
    String QUERY_SELECT_ALL =
        "SELECT * FROM " + IngredientRecord.TABLE_NAME;

    String QUERY_SELECT_BY_RECIPE_ID =
        "SELECT * FROM " + IngredientRecord.TABLE_NAME
        + " WHERE " + IngredientRecord.RECIPE_ID_COLUMN_NAME + " = :recipeId"
        + " ORDER BY " + IngredientRecord.ID_COLUMN_NAME;

    String QUERY_DELETE_ALL_BY_RECIPE_ID =
        "DELETE FROM " + IngredientRecord.TABLE_NAME
        + " WHERE " + IngredientRecord.RECIPE_ID_COLUMN_NAME + " = :recipeId";

    @Query(QUERY_SELECT_ALL)
    List<IngredientRecord> getAll();

    @Query(QUERY_SELECT_BY_RECIPE_ID)
    List<IngredientRecord> getByRecipeId(long recipeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(IngredientRecord ingredient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(List<IngredientRecord> ingredients);

    @Query(QUERY_DELETE_ALL_BY_RECIPE_ID)
    void deleteAllByRecipeId(long recipeId);
}
