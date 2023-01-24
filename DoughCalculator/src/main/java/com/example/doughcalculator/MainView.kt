package com.example.doughcalculator

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun calculateWaterPercent()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun calculateSaltPercent()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun calculateSugarPercent()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun calculateButterPercent()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun recalculateWaterGram()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun recalculateSaltGram()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun recalculateSugarGram()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun recalculateButterGram()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun calculateAll()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun validate()
}