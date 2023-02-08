package com.example.doughcalculator.data

interface BaseRecipeModel {
    var recipeId: Long
    var recipeTitle: String
    var description: String
    var isFavorite: Boolean
}