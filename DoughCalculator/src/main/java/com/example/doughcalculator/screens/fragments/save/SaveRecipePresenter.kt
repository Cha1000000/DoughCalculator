package com.example.doughcalculator.screens.fragments.save

import androidx.lifecycle.MutableLiveData
import com.example.doughcalculator.common.extensions.launchUI
import com.example.doughcalculator.common.extensions.withIO
import com.example.doughcalculator.common.mvp.BasePresenter
import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.database.*
import com.example.doughcalculator.screens.main.MainActivity
import moxy.InjectViewState
import org.koin.core.component.inject

@InjectViewState
class SaveRecipePresenter : BasePresenter<SaveRecipeView>() {

    private val ratioModel: BaseRatioModel by inject()
    private val dataSource: DoughRecipeDao by inject()

    private suspend fun update(recipe: DoughRecipeEntity) {
        dataSource.update(recipe)
    }

    private suspend fun insert(recipe: DoughRecipeEntity) {
        dataSource.insert(recipe)
    }

    private suspend fun getAll() =
        dataSource.getAllRecipes()

    private suspend fun getByTitle(title: String) =
        dataSource.getByTitle(title)

    fun onRecipeSave() {
        if (ratioModel.isUpdate()) {
            launchUI (createAlertErrorHandler()) {
                withIO {
                    val entity = mapToEntity(ratioModel)
                    update(entity)
                    ratioModel.recipeId = getByTitle(entity.title).recipeId
                }
            }
        } else {
            launchUI (createAlertErrorHandler()) {
                withIO {
                    val entity = mapToEntity(ratioModel)
                    insert(entity)
                    ratioModel.recipeId = getByTitle(entity.title).recipeId
                }
            }
        }
        viewState.saveRecipe()
    }

}