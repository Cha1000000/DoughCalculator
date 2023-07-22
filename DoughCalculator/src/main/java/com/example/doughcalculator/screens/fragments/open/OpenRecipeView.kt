package com.example.doughcalculator.screens.fragments.open

import com.example.doughcalculator.common.mvp.BaseView
import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.data.BaseRecipeModel
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface OpenRecipeView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun loadRecipeList(items: List<BaseRecipeModel>)

    fun openRecipe(model: BaseRatioModel)

    fun showRemoveRecipeConfirmDialog(recipe: BaseRecipeModel)
}