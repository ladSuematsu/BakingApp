package com.ladsoft.bakingapp.data.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = RecipeRecord.TABLE_NAME)
public class RecipeRecord {
    public static final String TABLE_NAME = "recipe";

    public static final String ID_COLUMN_NAME = "id";
    public static final String NAME_COLUMN_NAME = "name";
    public static final String SERVINGS_COLUMN_NAME = "servings";
    public static final String IMAGE_COLUMN_NAME = "image_url";

    @PrimaryKey @ColumnInfo(name = ID_COLUMN_NAME) long id;
    @ColumnInfo(name = NAME_COLUMN_NAME) String name;
    @ColumnInfo(name = SERVINGS_COLUMN_NAME) int servings;
    @ColumnInfo(name = IMAGE_COLUMN_NAME) String imageUrl;

    public RecipeRecord(long id, String name, int servings, String imageUrl) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
