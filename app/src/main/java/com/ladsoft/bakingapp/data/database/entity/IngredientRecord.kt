package com.ladsoft.bakingapp.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index

@Entity(tableName = IngredientRecord.TABLE_NAME,
    primaryKeys = [IngredientRecord.ID_COLUMN_NAME, IngredientRecord.RECIPE_ID_COLUMN_NAME],
    foreignKeys =[
            ForeignKey(entity = RecipeRecord::class,
                parentColumns = [RecipeRecord.ID_COLUMN_NAME],
                childColumns = [IngredientRecord.RECIPE_ID_COLUMN_NAME],
                onDelete = ForeignKey.CASCADE
            )
        ],
    indices = [
            Index(value = [IngredientRecord.RECIPE_ID_COLUMN_NAME, IngredientRecord.ID_COLUMN_NAME], unique = true)
        ]
)
data class IngredientRecord(
        @ColumnInfo(name = ID_COLUMN_NAME) val id: Long,
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