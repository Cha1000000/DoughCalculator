package com.example.doughcalculator.data

import androidx.lifecycle.MutableLiveData

interface BaseRecipeModel {

    var recipeId: Long
    var title: MutableLiveData<String>
    var description: MutableLiveData<String>
    var isFavorite: Boolean
}