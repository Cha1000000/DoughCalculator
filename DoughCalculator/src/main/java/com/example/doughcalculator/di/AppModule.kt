package com.example.doughcalculator.di

import com.example.doughcalculator.data.BaseViewModel
import com.example.doughcalculator.data.RatioModel
import org.koin.dsl.module

val appModule = module {
    single<BaseViewModel> { RatioModel() }
}