package com.ladsoft.bakingapp.data.database.dao

import android.arch.persistence.room.*
import com.ladsoft.bakingapp.data.database.entity.IngredientRecord

@Dao
interface IngredientDao {
    @Query(QUERY_SELECT_ALL)
    fun getAll(): List<IngredientRecord>

    @Query(QUERY_SELECT_BY_RECIPE_ID)
    fun getByRecipeId(recipeId : Long): List<IngredientRecord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(ingredient: IngredientRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(ingredients: List<IngredientRecord>)

    @Query(QUERY_DELETE_ALL_BY_RECIPE_ID)
    fun deleteAllByRecipeId(recipeId : Long)

    private companion object {
        const val QUERY_SELECT_ALL =
                "SELECT * FROM ${IngredientRecord.TABLE_NAME}"

        const val QUERY_SELECT_BY_RECIPE_ID =
                """
                SELECT * FROM ${IngredientRecord.TABLE_NAME}
                WHERE ${IngredientRecord.RECIPE_ID_COLUMN_NAME} = :recipeId
                ORDER BY ${IngredientRecord.ID_COLUMN_NAME}
                """

        const val QUERY_DELETE_ALL_BY_RECIPE_ID =
                """
                DELETE FROM ${IngredientRecord.TABLE_NAME}
                WHERE ${IngredientRecord.RECIPE_ID_COLUMN_NAME} = :recipeId
                """
    }
}
