package com.example.doughcalculator.common.mvp

import com.example.doughcalculator.common.extensions.hideKeyboard
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
}