package com.example.doughcalculator.screens.fragments.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.doughcalculator.R
import com.example.doughcalculator.common.mvp.BaseFragment
import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.data.RatioModel
import com.example.doughcalculator.databinding.FragmentSaveRecipeBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.inject

class SaveRecipeFragment : BaseFragment(), SaveRecipeView {

    private lateinit var binding: FragmentSaveRecipeBinding
    private val ratioModel: BaseRatioModel  by inject()

    @InjectPresenter
    internal lateinit var presenter: SaveRecipePresenter

    @ProvidePresenter
    fun providePresenter() = SaveRecipePresenter()

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
        initFragment()
        setBackButtonPressedListener {
            findNavController()
                .navigate(SaveRecipeFragmentDirections.actionSaveRecipeDestinationToCalculationDestination())
        }
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun initFragment() = with(binding) {
        ratio = ratioModel as RatioModel?
        btSave.isEnabled = false
        btSave.setOnClickListener { presenter.onRecipeSave() }
        textTitle.addTextChangedListener { btSave.isEnabled = it?.isNotEmpty() ?: false }
    }

    override fun saveRecipe() {
        findNavController().navigate(SaveRecipeFragmentDirections.actionSaveRecipeDestinationToCalculationDestination())
    }
}