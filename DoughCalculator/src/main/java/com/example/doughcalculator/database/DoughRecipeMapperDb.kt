package com.example.doughcalculator.database

import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.data.RecipeTitleModel

fun mapToDb(ratioModels: List<BaseRatioModel>) = ratioModels.map {
    with(it) {
        DoughRecipeEntity(
            recipeId,
            title,
            description,
            isFavorite,
            flourGram ?: 0,
            waterGram ?: 0,
            saltGram ?: 0,
            sugarGram ?: 0,
            butterGram ?: 0,
            flourGramCorrection ?: 0,
            waterGramCorrection.get() ?: 0,
            saltGramCorrection.get() ?: 0,
            sugarGramCorrection.get() ?: 0,
            butterGramCorrection.get() ?: 0,
            waterPercent.get() ?: 0.0,
            saltPercent.get() ?: 0.0,
            sugarPercent.get() ?: 0.0,
            butterPercent.get() ?: 0.0,
        )
    }
}

fun mapToEntity(ratioModel: BaseRatioModel) = with(ratioModel) {
    DoughRecipeEntity(
        recipeId,
        title,
        description,
        isFavorite,
        flourGram ?: 0,
        waterGram ?: 0,
        saltGram ?: 0,
        sugarGram ?: 0,
        butterGram ?: 0,
        flourGramCorrection ?: 0,
        waterGramCorrection.get() ?: 0,
        saltGramCorrection.get() ?: 0,
        sugarGramCorrection.get() ?: 0,
        butterGramCorrection.get() ?: 0,
        waterPercent.get() ?: 0.0,
        saltPercent.get() ?: 0.0,
        sugarPercent.get() ?: 0.0,
        butterPercent.get() ?: 0.0,
    )
}

fun mapToModels(entities: List<DoughRecipeEntity>) = entities.map {
    with(it) { RecipeTitleModel(recipeId, title, description, isFavorite) }
}

fun BaseRatioModel.mapFromEntity(entity: DoughRecipeEntity) =
    this.apply {
        recipeId = entity.recipeId
        title = entity.title
        description = entity.description
        isFavorite = entity.isFavorite
        flourGram = entity.flourGram
        waterGram = entity.waterGram
        saltGram = entity.saltGram
        sugarGram = entity.sugarGram
        butterGram = entity.butterGram
        flourGramCorrection = entity.flourGramCorrection
        waterGramCorrection.set(entity.waterGramCorrection)
        saltGramCorrection.set(entity.saltGramCorrection)
        sugarGramCorrection.set(entity.sugarGramCorrection)
        butterGramCorrection.set(entity.butterGramCorrection)
        waterPercent.set(entity.waterPercent)
        saltPercent.set(entity.saltPercent)
        sugarPercent.set(entity.sugarPercent)
        butterPercent.set(entity.butterPercent)
    }
