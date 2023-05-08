package com.example.doughcalculator.screens.fragments.open

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.doughcalculator.common.extensions.launchUI
import com.example.doughcalculator.common.extensions.withIO
import com.example.doughcalculator.common.mvp.BasePresenter
import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.data.BaseRecipeModel
import com.example.doughcalculator.database.DoughRecipeDao
import com.example.doughcalculator.database.mapFromEntity
import com.example.doughcalculator.database.mapToModels
import moxy.InjectViewState
import org.koin.core.component.inject

@InjectViewState
class OpenRecipePresenter : BasePresenter<OpenRecipeView>() {

    private val dataSource: DoughRecipeDao by inject()
    private var myRecipes = MutableLiveData<List<BaseRecipeModel>?>()
    private val recipes = dataSource.getAllRecipesLive().asLiveData()
    private val model: BaseRatioModel by inject()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        recipes.observeForever { allRecipes ->
            myRecipes.value = mapToModels(allRecipes)
            viewState.loadRecipeList(myRecipes.value!!)
        }
    }

    fun onRecipeSelect(recipe: BaseRecipeModel) {
        launchUI(createAlertErrorHandler()) {
            val recipeData = dataSource.getById(recipe.recipeId)
            model.mapFromEntity(recipeData)
            viewState.openRecipe(model)
        }
    }

    fun onDeleteRecipeClick(recipe: BaseRecipeModel) {
        viewState.showRemoveRecipeConfirmDialog(recipe)
    }

    fun onDeleteConfirmClick(recipe: BaseRecipeModel) {
        launchUI(createAlertErrorHandler()) {
            withIO { dataSource.deleteById(recipe.recipeId) }
        }
    }

    fun onRecipeSetFavorite(recipe: BaseRecipeModel) {
        recipe.isFavorite = !recipe.isFavorite
        launchUI(createAlertErrorHandler()) {
            withIO {
                val entity = dataSource.getById(recipe.recipeId)
                entity.isFavorite = recipe.isFavorite
                dataSource.update(entity)
            }
        }
    }
}