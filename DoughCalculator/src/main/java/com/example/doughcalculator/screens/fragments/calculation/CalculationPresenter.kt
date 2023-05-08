package com.example.doughcalculator.screens.fragments.calculation

import androidx.lifecycle.asLiveData
import com.example.doughcalculator.R
import com.example.doughcalculator.common.mvp.BasePresenter
import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.data.clear
import com.example.doughcalculator.database.DoughRecipeDao
import com.example.doughcalculator.screens.main.MainActivity
import moxy.InjectViewState
import org.koin.core.component.get
import org.koin.core.component.inject

@InjectViewState
class CalculationPresenter : BasePresenter<CalculationView>() {

    private val dataSource: DoughRecipeDao by inject()
    private var ratio: BaseRatioModel = get()
    private var currentRecipeId = ratio.recipeId
    private var ratioOriginalState = ratio.clone()
    private val recipes = dataSource.getAllRecipesLive().asLiveData()
    private var isError = false
    private var hasRecipes = false
    private var hasSaltValidationError = false
    private var hasWaterValidationError = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        recipes.observeForever { allRecipes ->
            hasRecipes = allRecipes.isNotEmpty()
        }
    }

    fun onCalculate() {
        if (ratio.flourGram == null
            || ratio.flourGram!! == MainActivity.SHORT_ZERO
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

        if (ratio.flourGramCorrection != null
            && ratio.flourGramCorrection!! > MainActivity.SHORT_ZERO
        ) {
            recalculateWaterGram()
            recalculateSaltGram()
            recalculateSugarGram()
            recalculateButterGram()
        }

        if (getIsCalculationChanged())
            ratio.hasUnsavedDate = true

        viewState.hideKeyboard()
    }

    fun onShowSaveScreen() {
        if (!ratio.isUpdate() && !ratio.hasUnsavedDate) {
            viewState.showError(R.string.alert_save_empty_data, R.string.error_alert_title_warning)
            return
        }
        viewState.showSaveRecipeScreen()
    }

    fun onShowOpenScreen() {
        if (!hasRecipes) {
            viewState.showError(
                R.string.error_alert_description_no_saved_recipes,
                R.string.error_alert_title_info
            )
            return
        }
        viewState.showOpenRecipeScreen()
    }

    fun onRecipeChanged() {
        if (currentRecipeId != ratio.recipeId) {
            currentRecipeId = ratio.recipeId
            ratioOriginalState = ratio.clone()
            resetValidation()
        }
    }

    fun onCreateNewRecipeClick() {
        if (ratio.hasUnsavedDate) {
            viewState.showCreateRecipeConfirmDialog()
            return
        }
        createNewRecipe()
    }

    fun createNewRecipe() {
        ratio.clear()
        resetValidation()
    }

    private fun resetValidation() {
        if (hasWaterValidationError) {
            hasWaterValidationError = false
            viewState.hideWaterValidationMessage()
        }

        if (hasSaltValidationError) {
            hasSaltValidationError = false
            viewState.hideSaltValidationMessage()
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
            || ratio.waterGram!! == MainActivity.SHORT_ZERO
        ) {
            viewState.showError(R.string.error_invalid_water_input)
            isError = true
            return
        }
        ratio.waterPercent.set(calculateIngredientPercent(ratio.waterGram!!))
        isError = false

        hasWaterValidationError = if (ratio.waterPercent.get()!! !in 59.5..80.0) {
            if (hasWaterValidationError) return
            viewState.showWaterValidationMessage()
            true
        } else {
            if (!hasWaterValidationError) return
            viewState.hideWaterValidationMessage()
            false
        }
    }

    private fun calculateSaltPercent() {
        if (ratio.saltGram == null
            || ratio.saltGram!! == MainActivity.SHORT_ZERO
        ) {
            viewState.showError(R.string.error_invalid_salt_input)
            isError = true
            return
        }
        ratio.saltPercent.set(calculateIngredientPercent(ratio.saltGram!!))
        isError = false

        hasSaltValidationError = if (ratio.saltPercent.get()!! > 2.5) {
            if (hasSaltValidationError) return
            viewState.showSaltValidationMessage()
            true
        } else {
            if (!hasSaltValidationError) return
            viewState.hideSaltValidationMessage()
            false
        }
    }

    private fun calculateSugarPercent() {
        if (ratio.sugarGram == null
            || ratio.sugarGram!! == MainActivity.SHORT_ZERO
        ) {
            ratio.sugarPercent.set(null)
            return
        }
        ratio.sugarPercent.set(calculateIngredientPercent(ratio.sugarGram!!))
    }

    private fun calculateButterPercent() {
        if (ratio.butterGram == null
            || ratio.butterGram!! == MainActivity.SHORT_ZERO
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
            || ratio.sugarGram!! == MainActivity.SHORT_ZERO
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
            || ratio.butterGram!! == MainActivity.SHORT_ZERO
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