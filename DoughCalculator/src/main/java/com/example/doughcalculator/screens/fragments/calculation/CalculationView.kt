package com.example.doughcalculator.screens.fragments.calculation

import androidx.annotation.StringRes
import com.example.doughcalculator.R
import com.example.doughcalculator.common.mvp.BaseView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CalculationView: BaseView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(@StringRes msgRes: Int, @StringRes titleRes: Int = R.string.error_alert_title_error)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showCreateRecipeConfirmDialog()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showWaterValidationMessage()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideWaterValidationMessage()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSaltValidationMessage()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideSaltValidationMessage()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSaveRecipeScreen()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showOpenRecipeScreen()
}