package com.example.doughcalculator.common.mvp

import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.doughcalculator.common.extensions.hideKeyboard
import com.example.doughcalculator.screens.fragments.open.OpenRecipeFragmentDirections
import moxy.MvpAppCompatFragment

abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    //protected val application = requireNotNull(this.activity).application

    override fun hideKeyboard() {
        view?.hideKeyboard()
    }

    protected fun closeFragment() {
        parentFragmentManager.popBackStack()
        parentFragmentManager
            .beginTransaction()
            .remove(this)
            .commit()
    }

    protected fun setBackButtonPressedListener(navigation: () -> Unit) {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigation()
        }
    }
}