package com.example.doughcalculator.di

import android.content.Context
import androidx.room.Room
import com.example.doughcalculator.common.exceptions.alert.AppErrorAlertShower
import com.example.doughcalculator.common.exceptions.alert.ErrorAlertShower
import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.data.BaseRecipeModel
import com.example.doughcalculator.data.RatioModel
import com.example.doughcalculator.data.RecipeTitleModel
import com.example.doughcalculator.database.DoughRecipeDao
import com.example.doughcalculator.database.DoughRecipesDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<DoughRecipeDao> { DoughRecipesDatabase.getInstance(get()).getRecipeDao() }
    single<BaseRecipeModel> { RecipeTitleModel(get(), get(), get(), get()) }
    single<BaseRatioModel> { RatioModel() }
    single<ErrorAlertShower> { AppErrorAlertShower() }
}