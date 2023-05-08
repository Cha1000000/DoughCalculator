package com.example.doughcalculator.screens.fragments.save

import com.example.doughcalculator.common.extensions.launchUI
import com.example.doughcalculator.common.extensions.withIO
import com.example.doughcalculator.common.mvp.BasePresenter
import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.database.DoughRecipeDao
import com.example.doughcalculator.database.mapToEntity
import moxy.InjectViewState
import org.koin.core.component.inject

@InjectViewState
class SaveRecipePresenter : BasePresenter<SaveRecipeView>() {

    private val dataSource: DoughRecipeDao by inject()
    lateinit var model: BaseRatioModel

    fun onRecipeSave() {
        val entity = mapToEntity(model)
        if (model.isUpdate()) {
            launchUI(createAlertErrorHandler()) {
                withIO { dataSource.update(entity) }
            }
        } else {
            launchUI(createAlertErrorHandler()) {
                withIO {
                    dataSource.insert(entity)
                    model.recipeId = dataSource.getByTitle(entity.title).recipeId
                }
            }
        }
        model.hasUnsavedDate = false
        viewState.saveRecipe()
    }
}