package com.example.doughcalculator.screens.main

import com.example.doughcalculator.R
import com.example.doughcalculator.screens.main.MainActivity.Companion.SHORT_ZERO
import com.example.doughcalculator.data.BaseViewModel
import moxy.InjectViewState
import moxy.MvpPresenter
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>(), KoinComponent {

    private val ratioModel: BaseViewModel by inject()
    private var isError = false

    fun onCalculate() {
        if (ratioModel.flourGram == null
            || ratioModel.flourGram!! == SHORT_ZERO
        ) {
            viewState.showError(R.string.error_invalid_flour_input)
            return
        }

        calculateWaterPercent()
        if (isError) return
        calculateSaltPercent()
        if (isError) return
        calculateSugarPercent()
        calculateButterPercent()

        viewState.validate()

        if (ratioModel.flourGramCorrection != null
            && ratioModel.flourGramCorrection!! > SHORT_ZERO) {
            recalculateWaterGram()
            recalculateSaltGram()
            recalculateSugarGram()
            recalculateButterGram()
        }
        viewState.closeKeyboard()
    }

    private fun calculateIngredientPercent(gram: Short): Double {
        return (gram * 100.00 / ratioModel.flourGram!!)
    }

    private fun recalculateIngredientGram(percent: Double, newGram: Short): Int {
        return (newGram * percent / 100.00).toInt()
    }

    private fun calculateWaterPercent() {
        if (ratioModel.waterGram == null
            || ratioModel.waterGram!! == SHORT_ZERO) {
            viewState.showError(R.string.error_invalid_water_input)
            isError = true
            return
        }
        ratioModel.waterPercent.set(calculateIngredientPercent(ratioModel.waterGram!!))
        isError = false
    }

    private fun calculateSaltPercent() {
        if (ratioModel.saltGram == null
            || ratioModel.saltGram!! == SHORT_ZERO) {
            viewState.showError(R.string.error_invalid_salt_input)
            isError = true
            return
        }
        ratioModel.saltPercent.set(calculateIngredientPercent(ratioModel.saltGram!!))
        isError = false
    }

    private fun calculateSugarPercent() {
        if (ratioModel.sugarGram == null
            || ratioModel.sugarGram!! == SHORT_ZERO) {
            ratioModel.sugarPercent.set(null)
            return
        }
        ratioModel.sugarPercent.set(calculateIngredientPercent(ratioModel.sugarGram!!))
    }

    private fun calculateButterPercent() {
        if (ratioModel.butterGram == null
            || ratioModel.butterGram!! == SHORT_ZERO) {
            ratioModel.butterPercent.set(null)
            return
        }
        ratioModel.butterPercent.set(calculateIngredientPercent(ratioModel.butterGram!!))
    }

    private fun recalculateWaterGram() {
        if (ratioModel.waterPercent.get() == null) {
            viewState.showError(R.string.error_invalid_water_percent)
            return
        }
        val percent = ratioModel.waterPercent.get()!!
        ratioModel.waterGramCorrection
            .set(recalculateIngredientGram(percent, ratioModel.flourGramCorrection!!).toShort())
    }

    private fun recalculateSaltGram() {
        if (ratioModel.saltPercent.get() == null) {
            viewState.showError(R.string.error_invalid_salt_percent)
            return
        }
        val percent = ratioModel.saltPercent.get()!!
        ratioModel.saltGramCorrection
            .set(recalculateIngredientGram(percent, ratioModel.flourGramCorrection!!).toShort())
    }

    private fun recalculateSugarGram() {
        if (ratioModel.sugarGram == null
            || ratioModel.sugarGram!! == SHORT_ZERO) {
            ratioModel.sugarGramCorrection.set(null)
            return
        }
        if (ratioModel.sugarPercent.get() == null) {
            viewState.showError(R.string.error_invalid_sugar_percent)
            return
        }
        val percent = ratioModel.sugarPercent.get()!!
        ratioModel.sugarGramCorrection
            .set(recalculateIngredientGram(percent, ratioModel.flourGramCorrection!!).toShort())
    }

    private fun recalculateButterGram() {
        if (ratioModel.butterGram == null
            || ratioModel.butterGram!! == SHORT_ZERO) {
            ratioModel.butterGramCorrection.set(null)
            return
        }
        if (ratioModel.butterPercent.get() == null) {
            viewState.showError(R.string.error_invalid_butter_percent)
            return
        }
        val percent = ratioModel.butterPercent.get()!!
        ratioModel.butterGramCorrection
            .set(recalculateIngredientGram(percent, ratioModel.flourGramCorrection!!).toShort())
    }
}