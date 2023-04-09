package com.example.doughcalculator.screens.fragments.save

import com.example.doughcalculator.common.mvp.BaseView
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface SaveRecipeView : BaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun saveRecipe()
}