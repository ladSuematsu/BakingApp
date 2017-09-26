package com.ladsoft.bakingapp.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.ladsoft.bakingapp.data.database.entity.IngredientRecord

@Dao
interface IngredientDao {
    @Query(QUERY_SELECT_ALL)
    fun getAll(): List<IngredientRecord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(recipe: IngredientRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(recipes: List<IngredientRecord>)

    private companion object {
        const val QUERY_SELECT_ALL =
                "SELECT * FROM " + IngredientRecord.TABLE_NAME
    }
}
