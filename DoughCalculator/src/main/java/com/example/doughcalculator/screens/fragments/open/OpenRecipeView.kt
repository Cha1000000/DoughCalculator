package com.example.doughcalculator.screens.fragments.open

import com.example.doughcalculator.common.mvp.BaseView
import com.example.doughcalculator.data.BaseRecipeModel
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface OpenRecipeView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun loadRecipeList(items: List<BaseRecipeModel>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openRecipe()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun removeRecipe(item: BaseRecipeModel)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showRemoveRecipeConfirmDialog(recipe: BaseRecipeModel)

}