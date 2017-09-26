package com.ladsoft.bakingapp.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = StepRecord.TABLE_NAME,
    indices = arrayOf(
        Index(value = *arrayOf(StepRecord.RECIPE_ID_COLUMN_NAME, StepRecord.STEP_ID_COLUMN_NAME)
            , unique = true))
)
data class StepRecord(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = ID_COLUMN_NAME) val id: Long,
        @ColumnInfo(name = RECIPE_ID_COLUMN_NAME) val recipeId: Long,
        @ColumnInfo(name = STEP_ID_COLUMN_NAME) val stepId: Long,
        @ColumnInfo(name = SHORT_DESCRIPTION_COLUMN_NAME) val shortDescription: String,
        @ColumnInfo(name = DESCRIPTION_COLUMN_NAME) val description: String,
        @ColumnInfo(name = VIDEO_URL_COLUMN_NAME) val videoUrl: String,
        @ColumnInfo(name = THUMBNAIL_URL_COLUMN_NAME) val thumbnailUrl: String) {

    companion object {
        const val TABLE_NAME = "step"

        const val ID_COLUMN_NAME = "id"
        const val RECIPE_ID_COLUMN_NAME = "recipe_id"
        const val STEP_ID_COLUMN_NAME = "step_id"
        const val SHORT_DESCRIPTION_COLUMN_NAME = "short_description"
        const val DESCRIPTION_COLUMN_NAME = "description"
        const val VIDEO_URL_COLUMN_NAME = "video_url"
        const val THUMBNAIL_URL_COLUMN_NAME = "thumbnail_url"
    }
}