package com.example.doughcalculator.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DoughRecipeDao {

    @Insert
    fun insert(recipe: DoughRecipe)

    @Update
    fun update(recipe: DoughRecipe)

    @Query("SELECT * FROM dough_recipes_table WHERE title = :recipeTitle")
    fun get(recipeTitle: String): DoughRecipe

    @Query("SELECT * FROM dough_recipes_table ORDER.BY.is_favorite")
    fun getAllRecipes(): LiveData<List<DoughRecipe>>

    @Delete
    fun delete(recipe: DoughRecipe)

    //@Delete
    //fun deleteRecipes(recipes: List<DoughRecipe>): Int

    @Query("DELETE FROM dough_recipes_table")
    fun clear()
}