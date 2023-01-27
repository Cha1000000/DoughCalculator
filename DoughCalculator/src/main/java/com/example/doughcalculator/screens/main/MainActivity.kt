package com.example.doughcalculator.screens.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import com.example.doughcalculator.R
import com.example.doughcalculator.common.extensions.getColorResCompat
import com.example.doughcalculator.common.extensions.hideKeyboard
import com.example.doughcalculator.common.extensions.showErrorAlertDialog
import com.example.doughcalculator.data.BaseViewModel
import com.example.doughcalculator.data.RatioModel
import com.example.doughcalculator.databinding.ActivityMainBinding
import com.google.android.material.internal.ViewUtils.hideKeyboard
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import org.koin.android.ext.android.inject

class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding
    //private val ratioModel by lazy { ViewModelProvider(this)[RatioModel::class.java] }
    private val ratioModel: BaseViewModel by inject()

    @InjectPresenter
    internal lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.ratio = ratioModel as RatioModel?
        binding.btCalculate.setOnClickListener { presenter.onCalculate() }
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        window.decorView.clearFocus()
    }

    companion object {
        const val FIRE_BUTTON_KEY_CODE = 293
        var SHORT_ZERO = 0.toShort()
    }

    override fun showError(@StringRes msgRes: Int) {
        this.showErrorAlertDialog(msgRes)
    }

    override fun closeKeyboard() {
        hideKeyboard()
    }

    @SuppressLint("ResourceAsColor")
    override fun validate() {
        // assert ratioModel.waterPercent is not null
        if (ratioModel.waterPercent.get()!! in 59.5..80.0) {
            binding.tvWaterValidation.visibility = View.GONE
            binding.etWaterGrams.setTextColor(applicationContext!!.getColorResCompat(android.R.attr.textColorPrimary))
            binding.tvWaterPercent.setTextColor(applicationContext!!.getColorResCompat(android.R.attr.textColorSecondary))
            binding.tvWaterGramsCorrection.setTextColor(applicationContext!!.getColorResCompat(android.R.attr.textColorSecondary))
        } else {
            binding.tvWaterValidation.visibility = View.VISIBLE
            binding.etWaterGrams.setTextColor(getColor(R.color.text_red))
            binding.tvWaterPercent.setTextColor(getColor(R.color.text_red))
            binding.tvWaterGramsCorrection.setTextColor(getColor(R.color.text_red))
        }
        // assert ratioModel.saltPercent is not null
        if (ratioModel.saltPercent.get()!! > 2.5) {
            binding.tvSaltValidation.visibility = View.VISIBLE
            binding.etSaltGrams.setTextColor(getColor(R.color.text_red))
            binding.tvSaltPercent.setTextColor(getColor(R.color.text_red))
            binding.tvSaltGramsCorrection.setTextColor(getColor(R.color.text_red))
        } else {
            binding.tvSaltValidation.visibility = View.GONE
            binding.etSaltGrams.setTextColor(applicationContext!!.getColorResCompat(android.R.attr.textColorPrimary))
            binding.tvSaltPercent.setTextColor(applicationContext!!.getColorResCompat(android.R.attr.textColorSecondary))
            binding.tvSaltGramsCorrection.setTextColor(applicationContext!!.getColorResCompat(android.R.attr.textColorSecondary))
        }
    }
}