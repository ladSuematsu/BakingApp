package com.ladsoft.bakingapp.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.ladsoft.bakingapp.BuildConfig
import com.ladsoft.bakingapp.data.database.dao.IngredientDao
import com.ladsoft.bakingapp.data.database.dao.RecipeDao
import com.ladsoft.bakingapp.data.database.dao.StepDao
import com.ladsoft.bakingapp.data.database.entity.IngredientRecord
import com.ladsoft.bakingapp.data.database.entity.RecipeRecord
import com.ladsoft.bakingapp.data.database.entity.StepRecord

@Database(entities = [RecipeRecord::class, IngredientRecord::class, StepRecord::class],
            version = AppDatabase.DB_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun stepDao(): StepDao

    companion object {
        const val DB_NAME = BuildConfig.DB_NAME
        const val DB_VERSION = BuildConfig.DB_VERSION
        var sLock = Any()

        fun getDatabase(context: Context) : AppDatabase = synchronized(sLock) {
                return Room.databaseBuilder<AppDatabase>(
                            context.applicationContext,
                            AppDatabase::class.java,
                            DB_NAME)
                            .build()
        }
    }
}