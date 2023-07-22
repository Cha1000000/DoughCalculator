package com.example.doughcalculator.data

import androidx.lifecycle.MutableLiveData

data class RecipeTitleModel (
    override var recipeId: Long,
    override var title: MutableLiveData<String>,
    override var description: MutableLiveData<String>,
    override var isFavorite: Boolean
    ) : BaseRecipeModel {
    override fun toString(): String = title.value!!
}