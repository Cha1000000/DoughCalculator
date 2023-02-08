package com.example.doughcalculator.data

data class RecipeTitleModel (
    override var recipeId: Long,
    override var recipeTitle: String,
    override var description: String,
    override var isFavorite: Boolean
    ) : BaseRecipeModel