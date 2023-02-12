package com.example.doughcalculator.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DoughRecipeDao {

    @Insert
    fun insert(recipe: DoughRecipeEntity)

    @Update
    fun update(recipe: DoughRecipeEntity)

    @Query("SELECT * FROM dough_recipes_table WHERE title = :recipeTitle")
//    @Query("SELECT * FROM dough_recipes_table WHERE title LIKE :recipeTitle")
    fun getByTitle(recipeTitle: String): DoughRecipeEntity

    @Query("SELECT * FROM dough_recipes_table WHERE recipeId = :id")
//    @Query("SELECT * FROM dough_recipes_table WHERE recipeId LIKE :id")
    fun getById(id: Long): DoughRecipeEntity

    @Query("SELECT * FROM dough_recipes_table ORDER BY is_favorite")
    fun getAllRecipes(): List<DoughRecipeEntity>?

    @Delete
    fun delete(recipe: DoughRecipeEntity)

    //@Delete
    //fun deleteRecipes(recipes: List<DoughRecipe>): Int

    @Query("DELETE FROM dough_recipes_table")
    fun clear()
}