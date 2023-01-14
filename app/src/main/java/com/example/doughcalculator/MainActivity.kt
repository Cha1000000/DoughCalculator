package com.example.doughcalculator

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.doughcalculator.common.extensions.showErrorAlertDialog
import com.example.doughcalculator.data.RatioModel
import com.example.doughcalculator.databinding.ActivityMainBinding
import moxy.MvpAppCompatActivity
import androidx.annotation.MainThread as MainThread

class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding
    private val ratioModel by lazy { ViewModelProvider(this)[RatioModel::class.java] }
    //private val ratio = Ratio()
    private var isError = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.ratio = ratioModel
        binding.btCalculate.setOnClickListener { calculate() }
    }

    private fun calculate() {
        calculateAll()
        //binding.invalidateAll()
    }

    private fun calculateIngredientPercent(gram: Short): Double {
        return (gram * 100 / ratioModel.flourGram.value!!).toDouble()
    }

    private fun recalculateIngredientGram(percent: Double, newGram: Short): Int {
        return (newGram * percent / 100).toInt()
    }

    override fun calculateWaterPercent() {
        if (ratioModel.waterGram.value == null
            || ratioModel.waterGram.value!! == SHORT_ZERO) {
            this.showErrorAlertDialog(R.string.error_invalid_water_input)
            isError = true
            return
        }
        ratioModel.waterPercent.value = calculateIngredientPercent(ratioModel.waterGram.value!!)
        isError = false
    }

    override fun calculateSaltPercent() {
        if (ratioModel.saltGram.value == null
            || ratioModel.saltGram.value!! == SHORT_ZERO) {
            this.showErrorAlertDialog(R.string.error_invalid_salt_input)
            isError = true
            return
        }
        ratioModel.saltPercent.value = calculateIngredientPercent(ratioModel.saltGram.value!!)
        isError = false
    }

    override fun calculateSugarPercent() {
        if (ratioModel.sugarGram.value == null
            || ratioModel.sugarGram.value!! == SHORT_ZERO) {
            ratioModel.sugarPercent.value = null
            return
        }
        ratioModel.sugarPercent.value = calculateIngredientPercent(ratioModel.sugarGram.value!!)
    }

    override fun calculateButterPercent() {
        if (ratioModel.butterGram.value == null
            || ratioModel.butterGram.value!! == SHORT_ZERO) {
            ratioModel.butterPercent.value = null
            return
        }
        ratioModel.butterPercent.value = calculateIngredientPercent(ratioModel.butterGram.value!!)
    }

    override fun recalculateWaterGram() {
        if (ratioModel.waterPercent.value == null) {
            this.showErrorAlertDialog(R.string.error_invalid_water_percent)
            return
        }
        val percent = ratioModel.waterPercent.value!!
        ratioModel.waterGramCorrection.value = recalculateIngredientGram(percent, ratioModel.flourGramCorrection.value!!).toShort()
    }

    override fun recalculateSaltGram() {
        if (ratioModel.saltPercent.value == null) {
            this.showErrorAlertDialog(R.string.error_invalid_salt_percent)
            return
        }
        val percent = ratioModel.saltPercent.value!!
        ratioModel.saltGramCorrection.value = recalculateIngredientGram(percent, ratioModel.flourGramCorrection.value!!).toShort()
    }

    override fun recalculateSugarGram() {
        if (ratioModel.sugarGram.value == null
            || ratioModel.sugarGram.value!! == SHORT_ZERO) {
            ratioModel.sugarGramCorrection.value = null
            return
        }
        if (ratioModel.sugarPercent.value == null) {
            this.showErrorAlertDialog(R.string.error_invalid_sugar_percent)
            return
        }
        val percent = ratioModel.sugarPercent.value!!
        ratioModel.sugarGramCorrection.value = recalculateIngredientGram(percent, ratioModel.flourGramCorrection.value!!).toShort()
    }

    override fun recalculateButterGram() {
        if (ratioModel.butterGram.value == null
            || ratioModel.butterGram.value!! == SHORT_ZERO) {
            ratioModel.butterGramCorrection.value = null
            return
        }
        if (ratioModel.butterPercent.value == null) {
            this.showErrorAlertDialog(R.string.error_invalid_butter_percent)
            return
        }
        val percent = ratioModel.butterPercent.value!!
        ratioModel.butterGramCorrection.value = recalculateIngredientGram(percent, ratioModel.flourGramCorrection.value!!).toShort()
    }

    override fun calculateAll() {
        if (ratioModel.flourGram.value == null
            || ratioModel.flourGram.value!! == SHORT_ZERO) {
            this.showErrorAlertDialog(R.string.error_invalid_flour_input)
            return
        }

        calculateWaterPercent()
        if (isError) return
        calculateSaltPercent()
        if (isError) return
        calculateSugarPercent()
        calculateButterPercent()

        if (ratioModel.flourGramCorrection.value != null
            && ratioModel.flourGramCorrection.value!! > SHORT_ZERO) {
            recalculateWaterGram()
            recalculateSaltGram()
            recalculateSugarGram()
            recalculateButterGram()
        }
    }

    companion object {
        const val FIRE_BUTTON_KEY_CODE = 293
        var SHORT_ZERO = 0.toShort()
    }
}