package com.example.doughcalculator.di

import com.example.doughcalculator.data.RatioModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { RatioModel() }
}