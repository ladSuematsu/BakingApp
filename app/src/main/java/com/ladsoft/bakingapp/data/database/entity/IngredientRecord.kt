package com.ladsoft.bakingapp.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = IngredientRecord.TABLE_NAME)
data class IngredientRecord(
        @PrimaryKey @ColumnInfo(name = ID_COLUMN_NAME) val id: Long,
        @ColumnInfo(name = RECIPE_ID_COLUMN_NAME) val recipeId: Long,
        @ColumnInfo(name = QUANTITY_COLUMN_NAME) val quantity: Double,
        @ColumnInfo(name = MEASURE_COLUMN_NAME) val measure: String,
        @ColumnInfo(name = DESCRIPTION_COLUMN_NAME) val description: String) {

    companion object {
        const val TABLE_NAME = "ingredient"

        const val ID_COLUMN_NAME = "id"
        const val RECIPE_ID_COLUMN_NAME = "recipe_id"
        const val MEASURE_COLUMN_NAME = "measure"
        const val DESCRIPTION_COLUMN_NAME = "description"
        const val QUANTITY_COLUMN_NAME = "quantity"
    }
}