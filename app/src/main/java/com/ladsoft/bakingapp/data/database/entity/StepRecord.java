package com.ladsoft.bakingapp.data.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

@Entity(tableName = StepRecord.TABLE_NAME,
        primaryKeys = {StepRecord.RECIPE_ID_COLUMN_NAME, StepRecord.ID_COLUMN_NAME},
        foreignKeys = @ForeignKey(entity = RecipeRecord.class,
                        parentColumns = RecipeRecord.ID_COLUMN_NAME,
                        childColumns = StepRecord.RECIPE_ID_COLUMN_NAME,
                        onDelete = ForeignKey.CASCADE),
        indices = @Index(value = {StepRecord.RECIPE_ID_COLUMN_NAME, StepRecord.ID_COLUMN_NAME}, unique = true)
        )
public class StepRecord {
    public static final String TABLE_NAME = "step";

    public static final String ID_COLUMN_NAME = "id";
    public static final String RECIPE_ID_COLUMN_NAME = "recipe_id";
    public static final String SHORT_DESCRIPTION_COLUMN_NAME = "short_description";
    public static final String DESCRIPTION_COLUMN_NAME = "description";
    public static final String VIDEO_URL_COLUMN_NAME = "video_url";
    public static final String THUMBNAIL_URL_COLUMN_NAME = "thumbnail_url";

    @ColumnInfo(name = ID_COLUMN_NAME) private long id;
    @ColumnInfo(name = RECIPE_ID_COLUMN_NAME) private long recipeId;
    @ColumnInfo(name = SHORT_DESCRIPTION_COLUMN_NAME) private String shortDescription;
    @ColumnInfo(name = DESCRIPTION_COLUMN_NAME) private String description;
    @ColumnInfo(name = VIDEO_URL_COLUMN_NAME) private String videoUrl;
    @ColumnInfo(name = THUMBNAIL_URL_COLUMN_NAME) private String thumbnailUrl;

    public StepRecord(long id, long recipeId, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.recipeId = recipeId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public long getId() {
        return id;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
};
