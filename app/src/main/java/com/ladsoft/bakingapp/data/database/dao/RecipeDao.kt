package com.ladsoft.bakingapp.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.ladsoft.bakingapp.data.database.entity.RecipeRecord

@Dao
interface RecipeDao {
    @Query(QUERY_SELECT_ALL)
    fun getAll(): List<RecipeRecord>

    @Query(QUERY_SELECT_BY_ID)
    fun getById(id: Long): RecipeRecord

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(recipe: RecipeRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(recipes: List<RecipeRecord>)

    @Query(QUERY_DELETE_ALL)
    fun deleteAll()

    @Query(QUERY_DELETE_NOT_IN)
    fun deletePreserving(preserveIds : List<Long>)

    private companion object {

        const val QUERY_SELECT_ALL =
                "SELECT * FROM ${RecipeRecord.TABLE_NAME}"

        const val QUERY_SELECT_BY_ID =
                """
                SELECT * FROM ${RecipeRecord.TABLE_NAME}
                WHERE ${RecipeRecord.ID_COLUMN_NAME} = :id
                LIMIT 1
                """

        const val QUERY_DELETE_ALL =
                """
                DELETE FROM ${RecipeRecord.TABLE_NAME}
                """

        const val QUERY_DELETE_NOT_IN =
                "DELETE FROM ${RecipeRecord.TABLE_NAME} WHERE ${RecipeRecord.ID_COLUMN_NAME} NOT IN (:preserveIds)"
    }
}
