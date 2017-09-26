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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(recipe: StepRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(recipes: List<StepRecord>)

    private companion object {
        const val QUERY_SELECT_ALL =
                "SELECT * FROM " + StepRecord.TABLE_NAME
    }
}
