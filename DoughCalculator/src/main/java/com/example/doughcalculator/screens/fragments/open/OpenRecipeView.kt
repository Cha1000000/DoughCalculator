package com.example.doughcalculator.screens.fragments.open

import com.example.doughcalculator.common.mvp.BaseView
import com.example.doughcalculator.data.BaseRecipeModel
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface OpenRecipeView : BaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun loadRecipeList(items: List<BaseRecipeModel>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openRecipe()
}