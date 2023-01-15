package com.example.doughcalculator

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.doughcalculator.common.extensions.showErrorAlertDialog
import com.example.doughcalculator.data.RatioModel
import com.example.doughcalculator.databinding.ActivityMainBinding
import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding
    private val ratioModel by lazy { ViewModelProvider(this)[RatioModel::class.java] }
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
        return (gram * 100.00 / ratioModel.flourGram!!)
    }

    private fun recalculateIngredientGram(percent: Double, newGram: Short): Int {
        return (newGram * percent / 100.00).toInt()
    }

    override fun calculateWaterPercent() {
        if (ratioModel.waterGram == null
            || ratioModel.waterGram!! == SHORT_ZERO) {
            this.showErrorAlertDialog(R.string.error_invalid_water_input)
            isError = true
            return
        }
        ratioModel.waterPercent
            .set(calculateIngredientPercent(ratioModel.waterGram!!))
        isError = false
    }

    override fun calculateSaltPercent() {
        if (ratioModel.saltGram == null
            || ratioModel.saltGram!! == SHORT_ZERO) {
            this.showErrorAlertDialog(R.string.error_invalid_salt_input)
            isError = true
            return
        }
        ratioModel.saltPercent
            .set(calculateIngredientPercent(ratioModel.saltGram!!))
        isError = false
    }

    override fun calculateSugarPercent() {
        if (ratioModel.sugarGram == null
            || ratioModel.sugarGram!! == SHORT_ZERO) {
            ratioModel.sugarPercent.set(null)
            return
        }
        ratioModel.sugarPercent
            .set(calculateIngredientPercent(ratioModel.sugarGram!!))
    }

    override fun calculateButterPercent() {
        if (ratioModel.butterGram == null
            || ratioModel.butterGram!! == SHORT_ZERO) {
            ratioModel.butterPercent.set(null)
            return
        }
        ratioModel.butterPercent
            .set(calculateIngredientPercent(ratioModel.butterGram!!))
    }

    override fun recalculateWaterGram() {
        if (ratioModel.waterPercent.get() == null) {
            this.showErrorAlertDialog(R.string.error_invalid_water_percent)
            return
        }
        val percent = ratioModel.waterPercent.get()!!
        ratioModel.waterGramCorrection
            .set(recalculateIngredientGram(percent, ratioModel.flourGramCorrection!!).toShort())
    }

    override fun recalculateSaltGram() {
        if (ratioModel.saltPercent.get() == null) {
            this.showErrorAlertDialog(R.string.error_invalid_salt_percent)
            return
        }
        val percent = ratioModel.saltPercent.get()!!
        ratioModel.saltGramCorrection
            .set(recalculateIngredientGram(percent, ratioModel.flourGramCorrection!!).toShort())
    }

    override fun recalculateSugarGram() {
        if (ratioModel.sugarGram == null
            || ratioModel.sugarGram!! == SHORT_ZERO) {
            ratioModel.sugarGramCorrection.set(null)
            return
        }
        if (ratioModel.sugarPercent.get() == null) {
            this.showErrorAlertDialog(R.string.error_invalid_sugar_percent)
            return
        }
        val percent = ratioModel.sugarPercent.get()!!
        ratioModel.sugarGramCorrection
            .set(recalculateIngredientGram(percent, ratioModel.flourGramCorrection!!).toShort())
    }

    override fun recalculateButterGram() {
        if (ratioModel.butterGram == null
            || ratioModel.butterGram!! == SHORT_ZERO) {
            ratioModel.butterGramCorrection.set(null)
            return
        }
        if (ratioModel.butterPercent.get() == null) {
            this.showErrorAlertDialog(R.string.error_invalid_butter_percent)
            return
        }
        val percent = ratioModel.butterPercent.get()!!
        ratioModel.butterGramCorrection
            .set(recalculateIngredientGram(percent, ratioModel.flourGramCorrection!!).toShort())
    }

    override fun calculateAll() {
        if (ratioModel.flourGram == null
            || ratioModel.flourGram!! == SHORT_ZERO) {
            this.showErrorAlertDialog(R.string.error_invalid_flour_input)
            return
        }

        calculateWaterPercent()
        if (isError) return
        calculateSaltPercent()
        if (isError) return
        calculateSugarPercent()
        calculateButterPercent()

        if (ratioModel.flourGramCorrection != null
            && ratioModel.flourGramCorrection!! > SHORT_ZERO) {
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