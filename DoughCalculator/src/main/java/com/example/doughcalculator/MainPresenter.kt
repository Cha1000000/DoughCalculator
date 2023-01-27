package com.example.doughcalculator

import com.example.doughcalculator.data.RatioModel
import moxy.InjectViewState
import moxy.MvpPresenter
import org.koin.java.KoinJavaComponent.inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    private val ratioModel: RatioModel by inject(RatioModel::class.java)

    fun onCalculate() {
        viewState.calculateAll()
    }
}