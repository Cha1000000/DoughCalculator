package com.example.doughcalculator.common.mvp

import android.app.Activity
import android.view.Menu
import androidx.core.view.forEach
import com.example.doughcalculator.R
import com.example.doughcalculator.common.extensions.hideKeyboard
import moxy.MvpAppCompatActivity

abstract class BaseActivity : MvpAppCompatActivity(), BaseView {

    protected var mainActionBar: androidx.appcompat.app.ActionBar? = null
    protected lateinit var toolbarMenu: Menu

    private fun setDisplayToolbarBackButton(show: Boolean) {
        mainActionBar?.setDisplayHomeAsUpEnabled(show)
    }

    private fun setToolbarTitle(titleRes: Int) {
        mainActionBar?.title = getString(titleRes)
    }

    private fun hideMenuItems() {
        toolbarMenu.forEach { item ->
            item.isVisible = false
        }
    }

    protected fun initFragmentToolbar(titleRes: Int) {
        setDisplayToolbarBackButton(true)
        setToolbarTitle(titleRes)
        hideMenuItems()
    }

    protected fun resetToolbar() {
        setDisplayToolbarBackButton(false)
        setToolbarTitle(R.string.app_name)
    }

    protected fun backStackChangedListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                resetToolbar()
            }
        }
    }

    override fun hideKeyboard() {
        (this as Activity).hideKeyboard()
    }

}