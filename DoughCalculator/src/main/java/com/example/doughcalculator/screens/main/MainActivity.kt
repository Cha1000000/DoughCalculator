package com.example.doughcalculator.screens.main

import android.content.Context
import android.os.Bundle
import com.example.doughcalculator.R
import moxy.MvpAppCompatActivity


class MainActivity : MvpAppCompatActivity() {

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
    }
}
