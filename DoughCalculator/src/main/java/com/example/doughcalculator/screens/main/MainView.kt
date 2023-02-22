package com.example.doughcalculator.screens.main

import androidx.annotation.StringRes
import com.example.doughcalculator.R
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainView: MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(@StringRes msgRes: Int, @StringRes titleRes: Int = R.string.error_alert_title_error)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showCreateRecipeConfirmDialog()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun validate()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun closeKeyboard()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSaveRecipeDialog()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showOpenRecipeDialog()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun resetView()

}