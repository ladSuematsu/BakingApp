package com.ladsoft.bakingapp.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = RecipeRecord.TABLE_NAME)
data class RecipeRecord(
    @PrimaryKey @ColumnInfo(name = ID_COLUMN_NAME) val id: Long,
    @ColumnInfo(name = NAME_COLUMN_NAME) val name: String,
    @ColumnInfo(name = SERVINGS_COLUMN_NAME) val servings: Int,
    @ColumnInfo(name = IMAGE_COLUMN_NAME) val imageUrl: String) {

    companion object {
        const val TABLE_NAME = "recipe"

        const val ID_COLUMN_NAME = "id"
        const val NAME_COLUMN_NAME = "name"
        const val SERVINGS_COLUMN_NAME = "servings"
        const val IMAGE_COLUMN_NAME = "image_url"
    }
}