package com.example.doughcalculator.screens.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.addCallback
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.doughcalculator.R
import com.example.doughcalculator.common.callback.OnBackPressedListener
import com.example.doughcalculator.common.extensions.getColorResCompat
import com.example.doughcalculator.common.extensions.hideKeyboard
import com.example.doughcalculator.common.extensions.showErrorAlertDialog
import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.data.RatioModel
import com.example.doughcalculator.databinding.ActivityMainBinding
import com.example.doughcalculator.screens.fragments.open.OpenRecipeFragment
import com.example.doughcalculator.screens.fragments.save.SaveRecipeFragment
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import org.koin.android.ext.android.inject

class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding

    //private val ratioModel by lazy { ViewModelProvider(this)[RatioModel::class.java] }
    private val ratioModel: BaseRatioModel by inject()
    private var mainActionBar: androidx.appcompat.app.ActionBar? = null

    @InjectPresenter
    internal lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = this
        mainActionBar = supportActionBar
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.ratio = ratioModel as RatioModel?
        binding.btCalculate.setOnClickListener { presenter.onCalculate() }
        backButtonPressedListener()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        window.decorView.clearFocus()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_save -> presenter.onShowSaveDialog()
            R.id.mi_open -> presenter.onShowOpenDialog()
        }
        return true
    }

    private fun backButtonPressedListener() {
        onBackPressedDispatcher.addCallback(this ) {
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
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container) ?: return false
        return if (currentFragment is OnBackPressedListener) {
            currentFragment.onBackPressed()
        } else {
            false
        }
    }

    override fun showSaveRecipeDialog() {
        showFragment(SaveRecipeFragment.getInstance())
    }

    override fun showOpenRecipeDialog() {
        showFragment(OpenRecipeFragment.getInstance())
    }

    private fun showFragment(f: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, f)
            .commit()
    }

    override fun showError(@StringRes msgRes: Int) {
        this.showErrorAlertDialog(msgRes)
    }

    override fun closeKeyboard() {
        hideKeyboard()
    }

    @SuppressLint("ResourceAsColor")
    override fun validate() {
        // assert ratioModel.waterPercent is not null
        if (ratioModel.waterPercent.get()!! in 59.5..80.0) {
            binding.tvWaterValidation.visibility = View.GONE
            binding.etWaterGrams.setTextColor(applicationContext!!.getColorResCompat(android.R.attr.textColorPrimary))
            binding.tvWaterPercent.setTextColor(applicationContext!!.getColorResCompat(android.R.attr.textColorSecondary))
            binding.tvWaterGramsCorrection.setTextColor(
                applicationContext!!.getColorResCompat(
                    android.R.attr.textColorSecondary
                )
            )
        } else {
            binding.tvWaterValidation.visibility = View.VISIBLE
            binding.etWaterGrams.setTextColor(getColor(R.color.text_red))
            binding.tvWaterPercent.setTextColor(getColor(R.color.text_red))
            binding.tvWaterGramsCorrection.setTextColor(getColor(R.color.text_red))
        }
        // assert ratioModel.saltPercent is not null
        if (ratioModel.saltPercent.get()!! > 2.5) {
            binding.tvSaltValidation.visibility = View.VISIBLE
            binding.etSaltGrams.setTextColor(getColor(R.color.text_red))
            binding.tvSaltPercent.setTextColor(getColor(R.color.text_red))
            binding.tvSaltGramsCorrection.setTextColor(getColor(R.color.text_red))
        } else {
            binding.tvSaltValidation.visibility = View.GONE
            binding.etSaltGrams.setTextColor(applicationContext!!.getColorResCompat(android.R.attr.textColorPrimary))
            binding.tvSaltPercent.setTextColor(applicationContext!!.getColorResCompat(android.R.attr.textColorSecondary))
            binding.tvSaltGramsCorrection.setTextColor(
                applicationContext!!.getColorResCompat(
                    android.R.attr.textColorSecondary
                )
            )
        }
    }

    companion object {
        const val FIRE_BUTTON_KEY_CODE = 293
        const val SHORT_ZERO = 0.toShort()
        lateinit var appContext: Context
    }

}

fun Fragment.getMainActivity() = activity as MainActivity
