package com.example.doughcalculator.screens.fragments.calculation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.annotation.StringRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.content.ContextCompat.getColor
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.doughcalculator.R
import com.example.doughcalculator.common.extensions.getColorResCompat
import com.example.doughcalculator.common.extensions.showAlertDialog
import com.example.doughcalculator.common.extensions.showErrorAlertDialog
import com.example.doughcalculator.common.mvp.BaseFragment
import com.example.doughcalculator.data.BaseRatioModel
import com.example.doughcalculator.data.RatioModel
import com.example.doughcalculator.databinding.FragmentCalculationBinding
import com.example.doughcalculator.screens.main.MainActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get

class CalculationFragment : BaseFragment(), CalculationView {

    private lateinit var binding: FragmentCalculationBinding
    private var ratioModel: BaseRatioModel = get()

    @InjectPresenter
    internal lateinit var presenter: CalculationPresenter

    @ProvidePresenter
    fun providePresenter() = CalculationPresenter(ratioModel)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_calculation,
            container,
            false
        )
        initView()
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    private fun initView() = with(binding) {
        ratio = ratioModel as RatioModel
        btCalculate.setOnClickListener { presenter.onCalculate() }
        tvTitle.addTextChangedListener {
            tvTitle.visibility = if (it?.isNotEmpty()!!) View.VISIBLE else View.GONE
        }
        tvDescription.addTextChangedListener {
            tvDescription.visibility = if (it?.isNotEmpty()!!) View.VISIBLE else View.GONE
        }
        toolbarMain.apply {
            menu.apply {
                (this as MenuBuilder).setOptionalIconsVisible(true)
            }
            setOnMenuItemClickListener { item ->
                when (item?.itemId) {
                    R.id.mi_new -> presenter.onCreateNewRecipeClick()
                    R.id.mi_open -> presenter.onShowOpenDialog()
                    R.id.mi_save -> presenter.onShowSaveDialog()
                }
                true
            }
        }
        MainActivity.Title = tvTitle
        MainActivity.Description = tvDescription
    }

    override fun onResume() {
        super.onResume()
        presenter.onRecipeChanged()
    }

    override fun createNewRecipe(recipe: BaseRatioModel) = with(binding) {
        ratio = recipe as RatioModel
        MainActivity.Title = tvTitle
        MainActivity.Description = tvDescription
    }

    override fun showCreateRecipeConfirmDialog() {
        context?.showAlertDialog(
            titleRes = R.string.error_alert_title_warning,
            msgRes = R.string.recipe_create_confirm_message,
            okCallback = { presenter.createNewRecipe() }
        )
    }

    override fun showSaveRecipeDialog() {
        findNavController().navigate(CalculationFragmentDirections.actionCalculationToSaving(ratioModel))
    }

    override fun showOpenRecipeDialog() {
        findNavController().navigate(CalculationFragmentDirections.actionCalculationToRecipes(ratioModel))
    }

    override fun showError(@StringRes msgRes: Int, @StringRes titleRes: Int) {
        context?.showErrorAlertDialog(msgRes, titleRes)
    }

    override fun showWaterValidationMessage() = with(binding) {
        tvWaterValidation.visibility = View.VISIBLE
        etWaterGrams.setTextColor(getColor(requireContext(), R.color.text_red))
        tvWaterPercent.setTextColor(getColor(requireContext(), R.color.text_red))
        tvWaterGramsCorrection.setTextColor(getColor(requireContext(), R.color.text_red))
    }

    override fun hideWaterValidationMessage() = with(binding) {
        tvWaterValidation.visibility = View.GONE
        etWaterGrams.setTextColor(requireContext().getColorResCompat(android.R.attr.textColorPrimary))
        tvWaterPercent.setTextColor(requireContext().getColorResCompat(android.R.attr.textColorSecondary))
        tvWaterGramsCorrection.setTextColor(requireContext().getColorResCompat(android.R.attr.textColorSecondary))
    }

    override fun showSaltValidationMessage() = with(binding) {
        tvSaltValidation.visibility = View.VISIBLE
        etSaltGrams.setTextColor(getColor(requireContext(), R.color.text_red))
        tvSaltPercent.setTextColor(getColor(requireContext(), R.color.text_red))
        tvSaltGramsCorrection.setTextColor(getColor(requireContext(), R.color.text_red))
    }

    override fun hideSaltValidationMessage() = with(binding) {
        tvSaltValidation.visibility = View.GONE
        etSaltGrams.setTextColor(requireContext().getColorResCompat(android.R.attr.textColorPrimary))
        tvSaltPercent.setTextColor(requireContext().getColorResCompat(android.R.attr.textColorSecondary))
        tvSaltGramsCorrection.setTextColor(requireContext().getColorResCompat(android.R.attr.textColorSecondary))
    }
}