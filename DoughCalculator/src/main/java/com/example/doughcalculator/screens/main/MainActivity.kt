package com.example.doughcalculator.screens.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.example.doughcalculator.R
import com.example.doughcalculator.common.mvp.BaseActivity


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = this
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        window.decorView.clearFocus()
    }

    companion object {
        const val FIRE_BUTTON_KEY_CODE = 293
        const val SHORT_ZERO = 0.toShort()
        lateinit var appContext: Context

        @SuppressLint("StaticFieldLeak")
        lateinit var Title: TextView

        @SuppressLint("StaticFieldLeak")
        lateinit var Description: TextView
    }
}
