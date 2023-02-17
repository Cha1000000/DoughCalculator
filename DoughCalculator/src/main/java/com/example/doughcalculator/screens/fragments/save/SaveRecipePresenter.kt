package com.example.doughcalculator.screens.fragments.save

import com.example.doughcalculator.common.extensions.launchUI
import com.example.doughcalculator.common.extensions.withIO
import com.example.doughcalculator.common.mvp.BasePresenter
import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.database.DoughRecipeDao
import com.example.doughcalculator.database.mapToEntity
import com.example.doughcalculator.screens.main.MainActivity
import moxy.InjectViewState
import org.koin.core.component.inject

@InjectViewState
class SaveRecipePresenter : BasePresenter<SaveRecipeView>() {

    private val ratioModel: BaseRatioModel by inject()
    private val dataSource: DoughRecipeDao by inject()

    fun onRecipeSave() {
        val entity = mapToEntity(ratioModel)
        if (ratioModel.isUpdate()) {
            launchUI(createAlertErrorHandler()) {
                withIO { dataSource.update(entity) }
            }
            MainActivity.Title.text = ratioModel.title
            MainActivity.Description.text = ratioModel.description
        } else {
            launchUI(createAlertErrorHandler()) {
                withIO {
                    dataSource.insert(entity)
                    ratioModel.recipeId = dataSource.getByTitle(entity.title).recipeId
                }
            }
        }
        viewState.saveRecipe()
    }

}