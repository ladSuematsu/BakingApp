package com.ladsoft.bakingapp.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.ladsoft.bakingapp.data.database.entity.StepRecord

@Dao
interface StepDao {
    @Query(QUERY_SELECT_ALL)
    fun getAll(): List<StepRecord>

    @Query(QUERY_SELECT_BY_RECIPE_ID)
    fun getByRecipeId(recipeId : Long): List<StepRecord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(step: StepRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(step: List<StepRecord>)

    @Query(QUERY_DELETE_ALL_BY_RECIPE_ID)
    fun deleteAllByRecipeId(recipeId : Long)

    private companion object {
        const val QUERY_SELECT_ALL =
                "SELECT * FROM ${StepRecord.TABLE_NAME}"

        const val QUERY_SELECT_BY_RECIPE_ID =
                """
                SELECT * FROM ${StepRecord.TABLE_NAME}
                WHERE ${StepRecord.RECIPE_ID_COLUMN_NAME} = :recipeId
                ORDER BY ${StepRecord.ID_COLUMN_NAME} DESC
                """

        const val QUERY_DELETE_ALL_BY_RECIPE_ID =
                """
                DELETE FROM ${StepRecord.TABLE_NAME}
                WHERE ${StepRecord.RECIPE_ID_COLUMN_NAME} = :recipeId
                """
    }
}
