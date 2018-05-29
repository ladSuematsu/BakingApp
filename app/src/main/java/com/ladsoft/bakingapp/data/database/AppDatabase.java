package com.ladsoft.bakingapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ladsoft.bakingapp.BuildConfig;
import com.ladsoft.bakingapp.data.database.dao.IngredientDao;
import com.ladsoft.bakingapp.data.database.dao.RecipeDao;
import com.ladsoft.bakingapp.data.database.dao.StepDao;
import com.ladsoft.bakingapp.data.database.entity.IngredientRecord;
import com.ladsoft.bakingapp.data.database.entity.RecipeRecord;
import com.ladsoft.bakingapp.data.database.entity.StepRecord;

@Database(entities = {RecipeRecord.class, IngredientRecord.class, StepRecord.class},
        version = AppDatabase.DB_VERSION)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = BuildConfig.DB_NAME;
    public static final int DB_VERSION = BuildConfig.DB_VERSION;

    private static final Object sLock = new Object();

    abstract public RecipeDao recipeDao();
    abstract public IngredientDao ingredientDao();
    abstract public StepDao stepDao();

    public static AppDatabase getDatabase(Context context) {
        synchronized(sLock) {
            return Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class,
                                    DB_NAME).build();
        }
    }
}
