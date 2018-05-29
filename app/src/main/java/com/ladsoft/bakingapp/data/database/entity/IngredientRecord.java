package com.ladsoft.bakingapp.data.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

@Entity(tableName = IngredientRecord.TABLE_NAME,
        primaryKeys = {IngredientRecord.ID_COLUMN_NAME, IngredientRecord.RECIPE_ID_COLUMN_NAME},
        foreignKeys = @ForeignKey(entity = RecipeRecord.class,
                                    parentColumns = RecipeRecord.ID_COLUMN_NAME,
                                    childColumns = IngredientRecord.RECIPE_ID_COLUMN_NAME,
                                    onDelete = ForeignKey.CASCADE),
        indices = @Index(value = {IngredientRecord.RECIPE_ID_COLUMN_NAME, IngredientRecord.ID_COLUMN_NAME},
                        unique = true)
    )
public class IngredientRecord {
    public static final String TABLE_NAME = "ingredient";

    public static final String ID_COLUMN_NAME = "id";
    public static final String RECIPE_ID_COLUMN_NAME = "recipe_id";
    public static final String MEASURE_COLUMN_NAME = "measure";
    public static final String DESCRIPTION_COLUMN_NAME = "description";
    public static final String QUANTITY_COLUMN_NAME = "quantity";

    @ColumnInfo(name = ID_COLUMN_NAME) private long id;
    @ColumnInfo(name = RECIPE_ID_COLUMN_NAME) private long  recipeId;
    @ColumnInfo(name = QUANTITY_COLUMN_NAME) private double quantity;
    @ColumnInfo(name = MEASURE_COLUMN_NAME) private String  measure;
    @ColumnInfo(name = DESCRIPTION_COLUMN_NAME) private String description;

    public IngredientRecord(long id, long recipeId, double quantity, String measure, String description) {
        this.id = id;
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.measure = measure;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getDescription() {
        return description;
    }
}
