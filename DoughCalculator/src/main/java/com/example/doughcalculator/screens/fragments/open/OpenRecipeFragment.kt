package com.example.doughcalculator.screens.fragments.open

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.doughcalculator.common.mvp.BaseFragment
import com.example.doughcalculator.R
import com.example.doughcalculator.common.callback.OnBackPressedListener
import com.example.doughcalculator.databinding.FragmentRecipesListBinding

class OpenRecipeFragment: BaseFragment(), OnBackPressedListener {

    private lateinit var binding: FragmentRecipesListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipes_list,
            container,
            false
        )
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onBackPressed(): Boolean {
        closeFragment()
        return true
    }

    companion object {
        @JvmStatic
        fun getInstance() = OpenRecipeFragment()
    }

}