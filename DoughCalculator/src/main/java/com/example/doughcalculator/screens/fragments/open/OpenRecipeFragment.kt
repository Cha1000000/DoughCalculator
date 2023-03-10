package com.example.doughcalculator.screens.fragments.open

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doughcalculator.R
import com.example.doughcalculator.common.callback.OnBackPressedListener
import com.example.doughcalculator.common.mvp.BaseFragment
import com.example.doughcalculator.data.BaseRecipeModel
import com.example.doughcalculator.databinding.FragmentRecipesListBinding
import com.example.doughcalculator.common.extensions.showAlertDialog
import com.example.doughcalculator.data.BaseRatioModel
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.core.component.KoinComponent

class OpenRecipeFragment : BaseFragment(), OpenRecipeView, OnBackPressedListener, KoinComponent {

    private lateinit var binding: FragmentRecipesListBinding
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var ratioModel: BaseRatioModel

    @InjectPresenter
    internal lateinit var presenter: OpenRecipePresenter

    @ProvidePresenter
    fun providePresenter() = OpenRecipePresenter(ratioModel)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipes_list,
            container,
            false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun initList(recipes: List<BaseRecipeModel>) {
        binding.apply {
            rcView.setHasFixedSize(true)
            rcView.layoutManager =
                LinearLayoutManager(context) // GridLayoutManager(context, COLUMN_COUNT)

            recipeAdapter = RecipeAdapter(recipes as ArrayList<BaseRecipeModel>)
                .also { adapter ->
                    with(adapter) {
                        onItemClick = { recipe -> presenter.onRecipeSelect(recipe) }
                        onDeleteItemClick = { recipe -> presenter.onDeleteRecipeClick(recipe) }
                        onItemSetFavoriteClick = { recipe -> presenter.onRecipeSetFavorite(recipe) }
                    }
                }

            rcView.adapter = recipeAdapter
        }
    }

    override fun loadRecipeList(items: List<BaseRecipeModel>) {
        initList(items)
    }

    override fun openRecipe() {
        closeFragment()
    }

    override fun showRemoveRecipeConfirmDialog(recipe: BaseRecipeModel) {
        context?.showAlertDialog(
            titleRes = R.string.delete_confirm_title,
            msg = getString(R.string.recipe_delete_confirm_message, recipe.title),
            okCallback = { presenter.onDeleteConfirmClick(recipe) }
        )
    }

    override fun removeRecipe(item: BaseRecipeModel) {
        recipeAdapter.deleteItem(item)
    }

    override fun onBackPressed(): Boolean {
        closeFragment()
        return true
    }

    companion object {
        @JvmStatic
        fun getInstance(model: BaseRatioModel) =
            OpenRecipeFragment().apply { this.ratioModel = model }

        //const val COLUMN_COUNT = 1
    }

}