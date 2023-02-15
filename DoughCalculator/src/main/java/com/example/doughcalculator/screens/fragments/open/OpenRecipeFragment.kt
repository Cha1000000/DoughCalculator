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
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.core.component.KoinComponent

class OpenRecipeFragment : BaseFragment(), OpenRecipeView, OnBackPressedListener, KoinComponent {

    private lateinit var binding: FragmentRecipesListBinding
    private lateinit var recipeAdapter: RecipeAdapter

    @InjectPresenter
    internal lateinit var presenter: OpenRecipePresenter

    @ProvidePresenter
    fun providePresenter() = OpenRecipePresenter()

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
            rcView.layoutManager = LinearLayoutManager(context) // GridLayoutManager(context, COLUMN_COUNT)
            recipeAdapter = RecipeAdapter(recipes as ArrayList<BaseRecipeModel>)
                .also {adapter ->
                    adapter.onItemClick = { recipe -> presenter.onRecipeSelect(recipe) }
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

    override fun onBackPressed(): Boolean {
        closeFragment()
        return true
    }

    companion object {
        @JvmStatic
        fun getInstance() = OpenRecipeFragment()

        //const val COLUMN_COUNT = 1
    }

}