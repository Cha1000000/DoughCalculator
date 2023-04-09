package com.example.doughcalculator.screens.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.example.doughcalculator.R
import com.example.doughcalculator.common.mvp.BaseActivity
import com.example.doughcalculator.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = this
        mainActionBar = supportActionBar
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //binding.lifecycleOwner = this
        //backButtonPressedListener()
        //backStackChangedListener()
        //setContentView(binding.root)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        window.decorView.clearFocus()
    }
/*
    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu is MenuBuilder) menu.setOptionalIconsVisible(true)
        menuInflater.inflate(R.menu.main_menu, menu)
        toolbarMenu = menu!!
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> forwardEventToFragments()
            R.id.mi_new -> presenter.onCreateNewRecipeClick()
            R.id.mi_open -> presenter.onShowOpenDialog()
            R.id.mi_save -> presenter.onShowSaveDialog()
        }
        return true
    }

    override fun showCreateRecipeConfirmDialog() {
        showAlertDialog(
            titleRes = R.string.error_alert_title_warning,
            msgRes = R.string.recipe_create_confirm_message,
            okCallback = { presenter.createNewRecipe() }
        )
    }

    private fun backStackChangedListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                resetToolbar()
                presenter.onRecipeChanged()
            }
        }
    }

    private fun backButtonPressedListener() {
        onBackPressedDispatcher.addCallback(this) {
            val isNotHandled = forwardEventToFragments().not()
            if (isNotHandled) {
                val isEndOfBackStack = supportFragmentManager.backStackEntryCount == 0
                if (isEndOfBackStack) {
                    finish()
                } else {
                    Log.d("MainActivity", "back pressed, popBackStack")
                    onBackPressedDispatcher.onBackPressed()
                }
            } else {
                Log.d("MainActivity", "back pressed, handled by a fragment")
            }
        }
    }

    private fun forwardEventToFragments(): Boolean {
        resetToolbar()
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container) ?: return false
        return if (currentFragment is OnBackPressedListener) {
            currentFragment.onBackPressed()
        } else {
            false
        }
    }

    private fun showFragment(f: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, f)
            .addToBackStack(f.toString())
            .commit()
    }

    override fun showWaterValidationMessage() = with(binding) {
            tvWaterValidation.visibility = View.VISIBLE
            etWaterGrams.setTextColor(getColor(R.color.text_red))
            tvWaterPercent.setTextColor(getColor(R.color.text_red))
            tvWaterGramsCorrection.setTextColor(getColor(R.color.text_red))
        }

    override fun hideWaterValidationMessage() = with(binding) {
            tvWaterValidation.visibility = View.GONE
            etWaterGrams.setTextColor(applicationContext!!.getColorResCompat(android.R.attr.textColorPrimary))
            tvWaterPercent.setTextColor(applicationContext!!.getColorResCompat(android.R.attr.textColorSecondary))
            tvWaterGramsCorrection.setTextColor(applicationContext!!.getColorResCompat(android.R.attr.textColorSecondary))
        }

    override fun showSaltValidationMessage() = with(binding) {
        tvSaltValidation.visibility = View.VISIBLE
        etSaltGrams.setTextColor(getColor(R.color.text_red))
        tvSaltPercent.setTextColor(getColor(R.color.text_red))
        tvSaltGramsCorrection.setTextColor(getColor(R.color.text_red))
    }

    override fun hideSaltValidationMessage() = with(binding) {
        tvSaltValidation.visibility = View.GONE
        etSaltGrams.setTextColor(applicationContext!!.getColorResCompat(android.R.attr.textColorPrimary))
        tvSaltPercent.setTextColor(applicationContext!!.getColorResCompat(android.R.attr.textColorSecondary))
        tvSaltGramsCorrection.setTextColor(applicationContext!!.getColorResCompat(android.R.attr.textColorSecondary))
    }
*/
    companion object {
        const val FIRE_BUTTON_KEY_CODE = 293
        const val SHORT_ZERO = 0.toShort()
        lateinit var appContext: Context

        @SuppressLint("StaticFieldLeak")
        lateinit var Title: TextView

        @SuppressLint("StaticFieldLeak")
        lateinit var Description: TextView
    }

}

//fun Fragment.getMainActivity() = activity as MainActivity
