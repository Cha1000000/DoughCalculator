package com.example.doughcalculator.data

fun BaseRatioModel.clear(): BaseRatioModel = this.apply {
    recipeId = 0
    title.postValue("")
    description.postValue("")
    isFavorite = false
    flourGram = null
    waterGram = null
    saltGram = null
    sugarGram = null
    butterGram = null
    flourGramCorrection = null
    waterGramCorrection.set(null)
    saltGramCorrection.set(null)
    sugarGramCorrection.set(null)
    butterGramCorrection.set(null)
    waterPercent.set(null)
    saltPercent.set(null)
    sugarPercent.set(null)
    butterPercent.set(null)
}