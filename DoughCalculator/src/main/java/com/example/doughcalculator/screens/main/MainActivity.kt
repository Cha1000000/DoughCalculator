package com.example.doughcalculator.screens.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.addCallback
import androidx.annotation.StringRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.doughcalculator.R
import com.example.doughcalculator.common.callback.OnBackPressedListener
import com.example.doughcalculator.common.extensions.getColorResCompat
import com.example.doughcalculator.common.extensions.showAlertDialog
import com.example.doughcalculator.common.extensions.showErrorAlertDialog
import com.example.doughcalculator.common.mvp.BaseActivity
import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.data.RatioModel
import com.example.doughcalculator.databinding.ActivityMainBinding
import com.example.doughcalculator.screens.fragments.open.OpenRecipeFragment
import com.example.doughcalculator.screens.fragments.save.SaveRecipeFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get


class MainActivity : BaseActivity(), MainView {

    private lateinit var binding: ActivityMainBinding
    private var ratioModel: BaseRatioModel = get()

    @InjectPresenter
    internal lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = MainPresenter(ratioModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = this
        mainActionBar = supportActionBar
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        initView()
        backButtonPressedListener()
        backStackChangedListener()
        setContentView(binding.root)
    }

    private fun initView() = with(binding) {
        ratio = ratioModel as RatioModel?
        btCalculate.setOnClickListener { presenter.onCalculate() }
        tvTitle.addTextChangedListener {
            tvTitle.visibility = if (it?.isNotEmpty()!!) View.VISIBLE else View.GONE
        }
        tvDescription.addTextChangedListener {
            tvDescription.visibility = if (it?.isNotEmpty()!!) View.VISIBLE else View.GONE
        }
        Title = tvTitle
        Description = tvDescription
    }

    override fun onResume() {
        super.onResume()
        window.decorView.clearFocus()
    }

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

    override fun showSaveRecipeDialog() {
        initFragmentToolbar(R.string.screen_title_save_recipe)
        showFragment(SaveRecipeFragment.getInstance(ratioModel))
    }

    override fun showOpenRecipeDialog() {
        initFragmentToolbar(R.string.screen_title_open_recipe)
        showFragment(OpenRecipeFragment.getInstance(ratioModel))
    }

    override fun resetView() = with(binding) {
        ratioModel = RatioModel()
        presenter.apply { this.ratio = ratioModel }
        ratio = ratioModel as RatioModel?
        Title = tvTitle
        Description = tvDescription
    }

    private fun showFragment(f: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, f)
            .addToBackStack(f.toString())
            .commit()
    }

    override fun showError(@StringRes msgRes: Int, @StringRes titleRes: Int) {
        this.showErrorAlertDialog(msgRes, titleRes)
    }

    override fun closeKeyboard() {
        hideKeyboard()
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
