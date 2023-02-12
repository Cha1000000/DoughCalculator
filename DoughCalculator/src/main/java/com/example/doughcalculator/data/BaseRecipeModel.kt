package com.example.doughcalculator.data

interface BaseRecipeModel {
    var recipeId: Long
    var title: String
    var description: String
    var isFavorite: Boolean
}