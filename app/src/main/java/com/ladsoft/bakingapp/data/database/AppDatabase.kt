package com.ladsoft.bakingapp.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.ladsoft.bakingapp.BuildConfig
import com.ladsoft.bakingapp.data.database.dao.RecipeDao

@Database(entities = arrayOf(RecipeDao::class),
            version = AppDatabase.DB_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        const val DB_NAME = BuildConfig.DB_NAME
        const val DB_VERSION = BuildConfig.DB_VERSION
        var instance: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder<AppDatabase>(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME)
                        .build()
            }

            return instance
        }
    }
}