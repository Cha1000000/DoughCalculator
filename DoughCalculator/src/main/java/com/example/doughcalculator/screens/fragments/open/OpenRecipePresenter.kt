package com.example.doughcalculator.screens.fragments.open

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.doughcalculator.common.extensions.launchUI
import com.example.doughcalculator.common.extensions.withIO
import com.example.doughcalculator.common.mvp.BasePresenter
import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.data.BaseRecipeModel
import com.example.doughcalculator.database.DoughRecipeDao
//import com.example.doughcalculator.database.DoughRecipeEntity
import com.example.doughcalculator.database.mapFromEntity
import com.example.doughcalculator.database.mapToModels
import com.example.doughcalculator.screens.main.MainActivity
import moxy.InjectViewState
import org.koin.core.component.inject

@InjectViewState
class OpenRecipePresenter : BasePresenter<OpenRecipeView>() {

    private val ratioModel: BaseRatioModel by inject()
    private val dataSource: DoughRecipeDao by inject()
    //private var recipeSource = MutableLiveData<List<DoughRecipeEntity>?>()
    private var myRecipes = MutableLiveData<List<BaseRecipeModel>?>()
    private val recipes = dataSource.getAllRecipesLive().asLiveData()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        //loadRecipes()
        recipes.observeForever { allRecipes ->
            myRecipes.value = mapToModels(allRecipes)
            viewState.loadRecipeList(myRecipes.value!!)
        }
    }

    /*private fun loadRecipes() {
        launchUI(createAlertErrorHandler()) {
            recipeSource.value = withIO { dataSource.getAllRecipes() }
            recipeSource.value.let { source ->
                if (source != null) {
                    myRecipes.value = mapToModels(source)
                    viewState.loadRecipeList(myRecipes.value!!)
                }
            }
        }
    }*/

    private fun getRecipeById(id: Long) {
        val recipeData = dataSource.getById(id)
        ratioModel.mapFromEntity(recipeData)
    }

    fun onRecipeSelect(recipe: BaseRecipeModel) {
        launchUI(createAlertErrorHandler()) {
            withIO { getRecipeById(recipe.recipeId) }
            MainActivity.Title.text = ratioModel.title
            MainActivity.Description.text = ratioModel.description
            viewState.openRecipe()
        }
    }

    fun onDeleteRecipeClick(recipe: BaseRecipeModel) {
        viewState.showRemoveRecipeConfirmDialog(recipe)
    }

    fun onDeleteConfirmClick(recipe: BaseRecipeModel) {
        launchUI(createAlertErrorHandler()) {
            withIO { dataSource.deleteById(recipe.recipeId) }
            //viewState.removeRecipe(recipe)
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