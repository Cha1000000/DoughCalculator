package com.example.doughcalculator.screens.main

import androidx.annotation.StringRes
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainView: MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(@StringRes msgRes: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun validate()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun closeKeyboard()
}