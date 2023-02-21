package com.example.doughcalculator.screens.fragments.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.doughcalculator.R
import com.example.doughcalculator.common.callback.OnBackPressedListener
import com.example.doughcalculator.common.mvp.BaseFragment
import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.data.RatioModel
import com.example.doughcalculator.databinding.FragmentSaveRecipeBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class SaveRecipeFragment : BaseFragment(), SaveRecipeView, OnBackPressedListener {

    private lateinit var binding: FragmentSaveRecipeBinding
    private lateinit var ratioModel: BaseRatioModel

    @InjectPresenter
    internal lateinit var presenter: SaveRecipePresenter

    @ProvidePresenter
    fun providePresenter() = SaveRecipePresenter(ratioModel)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_save_recipe,
            container,
            false
        )
        binding.lifecycleOwner = this
        initFragment()
        return binding.root
    }

    private fun initFragment() = with(binding) {
        ratio = ratioModel as RatioModel?
        btSave.isEnabled = false
        btSave.setOnClickListener { presenter.onRecipeSave() }
        textTitle.addTextChangedListener { btSave.isEnabled = it?.isNotEmpty() ?: false }
    }

    override fun onBackPressed(): Boolean {
        closeFragment()
        return true
    }

    companion object {
        @JvmStatic
        fun getInstance(model: BaseRatioModel) =
            SaveRecipeFragment().apply { this.ratioModel = model }
    }

    override fun saveRecipe() {
        closeFragment()
    }
}