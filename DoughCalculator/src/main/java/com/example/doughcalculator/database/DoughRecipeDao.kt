package com.example.doughcalculator.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DoughRecipeDao {

    @Insert
    fun insert(recipe: DoughRecipeEntity)

    @Update
    fun update(recipe: DoughRecipeEntity)

    @Query("SELECT * FROM dough_recipes_table WHERE title = :recipeTitle")
    fun getByTitle(recipeTitle: String): DoughRecipeEntity

    @Query("SELECT * FROM dough_recipes_table WHERE recipeId = :id")
    fun getById(id: Long): DoughRecipeEntity

    @Query("SELECT * FROM dough_recipes_table ORDER BY is_favorite DESC")
    fun getAllRecipes(): List<DoughRecipeEntity>?

    @Query("SELECT * FROM dough_recipes_table ORDER BY is_favorite DESC")
    fun getAllRecipesLive(): Flow<List<DoughRecipeEntity>>

    @Delete
    fun delete(recipe: DoughRecipeEntity)

    @Query("DELETE FROM dough_recipes_table WHERE recipeId = :id")
    fun deleteById(id: Long)

    @Query("DELETE FROM dough_recipes_table")
    fun clear()
}