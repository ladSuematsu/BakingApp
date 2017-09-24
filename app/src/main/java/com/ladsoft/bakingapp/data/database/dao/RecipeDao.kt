package com.ladsoft.bakingapp.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.ladsoft.bakingapp.data.database.entity.RecipeRecord

@Dao
interface RecipeDao {
    @Query(QUERY_SELECT_ALL)
    fun getAllRecipe(): List<RecipeRecord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipe(recipe: RecipeRecord)

    private companion object {
        const val QUERY_SELECT_ALL =
                "SELECT * FROM " + RecipeRecord.RECIPE_TABLE_NAME
    }
}