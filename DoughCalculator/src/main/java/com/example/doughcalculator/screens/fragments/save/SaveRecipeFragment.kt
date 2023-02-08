package com.example.doughcalculator.screens.fragments.save

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.doughcalculator.R
import com.example.doughcalculator.common.mvp.BaseFragment
import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.data.RatioModel
import com.example.doughcalculator.database.DoughRecipeDao
import com.example.doughcalculator.database.DoughRecipeEntity
import com.example.doughcalculator.database.DoughRecipesDatabase
import com.example.doughcalculator.databinding.FragmentSaveRecipeBinding
import com.example.doughcalculator.screens.main.MainActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.inject

class SaveRecipeFragment : BaseFragment(), SaveRecipeView {

    private lateinit var binding: FragmentSaveRecipeBinding
    private val ratioModel: BaseRatioModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_save_recipe,
            container,
            false
        )
        binding.lifecycleOwner = this

        binding.ratio = ratioModel as RatioModel?

        initFragment()

        return binding.root
    }

    private fun initFragment() {
        binding.textTitle.addTextChangedListener {
            binding.btSave.isEnabled = it?.isNotEmpty() ?: false
        }
        binding.btSave.isEnabled = false
        binding.btSave.setOnClickListener {
            presenter.onRecipeSave()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SaveRecipeFragment()
    }

    @InjectPresenter
    internal lateinit var presenter: SaveRecipePresenter

    @ProvidePresenter
    fun providePresenter() = SaveRecipePresenter()

    override fun saveRecipe() {
        parentFragmentManager
            .beginTransaction()
            .remove(this)
            .commit()
    }
}