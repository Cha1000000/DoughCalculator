package com.example.doughcalculator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DoughRecipe::class], version = 1)
abstract class DoughRecipesDatabase : RoomDatabase() {

    abstract val recipeDao: DoughRecipeDao

    companion object{

        @Volatile
        private var INSTANCE: DoughRecipesDatabase? = null

        fun getInstance(context: Context) : DoughRecipesDatabase {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DoughRecipesDatabase::class.java,
                        "dough_recipes_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}