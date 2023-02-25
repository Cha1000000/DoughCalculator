package com.example.doughcalculator.screens.main

import androidx.lifecycle.asLiveData
import com.example.doughcalculator.R
import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.database.DoughRecipeDao
import com.example.doughcalculator.screens.main.MainActivity.Companion.SHORT_ZERO
import moxy.InjectViewState
import moxy.MvpPresenter
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@InjectViewState
class MainPresenter(var ratio: BaseRatioModel) : MvpPresenter<MainView>(), KoinComponent {

    private val dataSource: DoughRecipeDao by inject()
    private val recipes = dataSource.getAllRecipesLive().asLiveData()
    private var isError = false
    private var hasRecipes = false
    private var currentRecipeId = ratio.recipeId
    private var ratioOriginalState = ratio.clone()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        recipes.observeForever { allRecipes ->
            hasRecipes = allRecipes.isNotEmpty()
        }
    }

    fun onCreateNewRecipeClick() {
        if (ratio.hasUnsavedDate) {
            viewState.showCreateRecipeConfirmDialog()
            return
        }
        viewState.resetView()
    }

    fun createNewRecipe() {
        ratio.hasUnsavedDate = false
        viewState.resetView()
    }

    fun onCalculate() {
        if (ratio.flourGram == null
            || ratio.flourGram!! == SHORT_ZERO
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

        if (ratio.flourGramCorrection != null
            && ratio.flourGramCorrection!! > SHORT_ZERO
        ) {
            recalculateWaterGram()
            recalculateSaltGram()
            recalculateSugarGram()
            recalculateButterGram()
        }

        if (getIsCalculationChanged())
            ratio.hasUnsavedDate = true

        viewState.closeKeyboard()
    }

    fun onShowSaveDialog() {
        if (!ratio.isUpdate() && !ratio.hasUnsavedDate) {
            viewState.showError(R.string.alert_save_empty_data, R.string.error_alert_title_warning)
            return
        }
        viewState.showSaveRecipeDialog()
    }

    fun onShowOpenDialog() {
        if (!hasRecipes) {
            viewState.showError(
                R.string.error_alert_description_no_saved_recipes,
                R.string.error_alert_title_info
            )
            return
        }
        viewState.showOpenRecipeDialog()
    }

    fun onRecipeChanged() {
        if (currentRecipeId != ratio.recipeId) {
            currentRecipeId = ratio.recipeId
            ratioOriginalState = ratio.clone()
        }
    }

    private fun getIsCalculationChanged(): Boolean {
        return with(ratioOriginalState) {
            flourGram != ratio.flourGram ||
            waterGram != ratio.waterGram ||
            saltGram != ratio.saltGram ||
            flourGramCorrection != ratio.flourGramCorrection
        }
    }

    private fun calculateIngredientPercent(gram: Short): Double {
        return (gram * 100.00 / ratio.flourGram!!)
    }

    private fun recalculateIngredientGram(percent: Double, newGram: Short): Int {
        return (newGram * percent / 100.00).toInt()
    }

    private fun calculateWaterPercent() {
        if (ratio.waterGram == null
            || ratio.waterGram!! == SHORT_ZERO
        ) {
            viewState.showError(R.string.error_invalid_water_input)
            isError = true
            return
        }
        ratio.waterPercent.set(calculateIngredientPercent(ratio.waterGram!!))
        isError = false
    }

    private fun calculateSaltPercent() {
        if (ratio.saltGram == null
            || ratio.saltGram!! == SHORT_ZERO
        ) {
            viewState.showError(R.string.error_invalid_salt_input)
            isError = true
            return
        }
        ratio.saltPercent.set(calculateIngredientPercent(ratio.saltGram!!))
        isError = false
    }

    private fun calculateSugarPercent() {
        if (ratio.sugarGram == null
            || ratio.sugarGram!! == SHORT_ZERO
        ) {
            ratio.sugarPercent.set(null)
            return
        }
        ratio.sugarPercent.set(calculateIngredientPercent(ratio.sugarGram!!))
    }

    private fun calculateButterPercent() {
        if (ratio.butterGram == null
            || ratio.butterGram!! == SHORT_ZERO
        ) {
            ratio.butterPercent.set(null)
            return
        }
        ratio.butterPercent.set(calculateIngredientPercent(ratio.butterGram!!))
    }

    private fun recalculateWaterGram() {
        if (ratio.waterPercent.get() == null) {
            viewState.showError(R.string.error_invalid_water_percent)
            return
        }
        val percent = ratio.waterPercent.get()!!
        ratio.waterGramCorrection
            .set(recalculateIngredientGram(percent, ratio.flourGramCorrection!!).toShort())
    }

    private fun recalculateSaltGram() {
        if (ratio.saltPercent.get() == null) {
            viewState.showError(R.string.error_invalid_salt_percent)
            return
        }
        val percent = ratio.saltPercent.get()!!
        ratio.saltGramCorrection
            .set(recalculateIngredientGram(percent, ratio.flourGramCorrection!!).toShort())
    }

    private fun recalculateSugarGram() {
        if (ratio.sugarGram == null
            || ratio.sugarGram!! == SHORT_ZERO
        ) {
            ratio.sugarGramCorrection.set(null)
            return
        }
        if (ratio.sugarPercent.get() == null) {
            viewState.showError(R.string.error_invalid_sugar_percent)
            return
        }
        val percent = ratio.sugarPercent.get()!!
        ratio.sugarGramCorrection
            .set(recalculateIngredientGram(percent, ratio.flourGramCorrection!!).toShort())
    }

    private fun recalculateButterGram() {
        if (ratio.butterGram == null
            || ratio.butterGram!! == SHORT_ZERO
        ) {
            ratio.butterGramCorrection.set(null)
            return
        }
        if (ratio.butterPercent.get() == null) {
            viewState.showError(R.string.error_invalid_butter_percent)
            return
        }
        val percent = ratio.butterPercent.get()!!
        ratio.butterGramCorrection
            .set(recalculateIngredientGram(percent, ratio.flourGramCorrection!!).toShort())
    }
}