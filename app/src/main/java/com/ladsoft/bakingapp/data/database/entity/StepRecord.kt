package com.ladsoft.bakingapp.data.database.entity

import android.arch.persistence.room.*

@Entity(tableName = StepRecord.TABLE_NAME,
        primaryKeys = [(StepRecord.RECIPE_ID_COLUMN_NAME), (StepRecord.ID_COLUMN_NAME)],
        foreignKeys = [(ForeignKey(entity = RecipeRecord::class,
                parentColumns = [RecipeRecord.ID_COLUMN_NAME],
                childColumns = [StepRecord.RECIPE_ID_COLUMN_NAME],
                onDelete = ForeignKey.CASCADE
        ))],
        indices = [(Index(value = [StepRecord.RECIPE_ID_COLUMN_NAME, StepRecord.ID_COLUMN_NAME], unique = true))]
)
data class StepRecord(
        @ColumnInfo(name = ID_COLUMN_NAME) val id: Long,
        @ColumnInfo(name = RECIPE_ID_COLUMN_NAME) val recipeId: Long,
        @ColumnInfo(name = SHORT_DESCRIPTION_COLUMN_NAME) val shortDescription: String,
        @ColumnInfo(name = DESCRIPTION_COLUMN_NAME) val description: String,
        @ColumnInfo(name = VIDEO_URL_COLUMN_NAME) val videoUrl: String,
        @ColumnInfo(name = THUMBNAIL_URL_COLUMN_NAME) val thumbnailUrl: String) {

    companion object {
        const val TABLE_NAME = "step"

        const val ID_COLUMN_NAME = "id"
        const val RECIPE_ID_COLUMN_NAME = "recipe_id"
        const val SHORT_DESCRIPTION_COLUMN_NAME = "short_description"
        const val DESCRIPTION_COLUMN_NAME = "description"
        const val VIDEO_URL_COLUMN_NAME = "video_url"
        const val THUMBNAIL_URL_COLUMN_NAME = "thumbnail_url"
    }
}