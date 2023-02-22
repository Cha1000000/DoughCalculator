package com.example.doughcalculator.screens.fragments.open

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.doughcalculator.common.extensions.launchIO
import com.example.doughcalculator.common.extensions.launchUI
import com.example.doughcalculator.common.mvp.BasePresenter
import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.data.BaseRecipeModel
import com.example.doughcalculator.database.DoughRecipeDao
import com.example.doughcalculator.database.mapFromEntity
import com.example.doughcalculator.database.mapToModels
import com.example.doughcalculator.screens.main.MainActivity
import moxy.InjectViewState
import org.koin.core.component.inject

@InjectViewState
class OpenRecipePresenter(private val ratioModel: BaseRatioModel) :
    BasePresenter<OpenRecipeView>() {

    private val dataSource: DoughRecipeDao by inject()
    private var myRecipes = MutableLiveData<List<BaseRecipeModel>?>()
    private val recipes = dataSource.getAllRecipesLive().asLiveData()

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
            ratioModel.mapFromEntity(recipeData)
            MainActivity.Title.text = ratioModel.title
            MainActivity.Description.text = ratioModel.description
            viewState.openRecipe()
        }
    }

    fun onDeleteRecipeClick(recipe: BaseRecipeModel) {
        viewState.showRemoveRecipeConfirmDialog(recipe)
    }

    fun onDeleteConfirmClick(recipe: BaseRecipeModel) {
        launchIO(createAlertErrorHandler()) {
            dataSource.deleteById(recipe.recipeId)
        }
    }

    fun onRecipeSetFavorite(recipe: BaseRecipeModel) {
        recipe.isFavorite = !recipe.isFavorite
        launchIO(createAlertErrorHandler()) {
            val entity = dataSource.getById(recipe.recipeId)
            entity.isFavorite = recipe.isFavorite
            dataSource.update(entity)
        }
    }
}