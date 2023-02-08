package com.example.doughcalculator.common.exceptions.alert

import android.content.Context
import androidx.annotation.StringRes
import com.example.doughcalculator.R
import com.example.doughcalculator.common.exceptions.ExceptionInfo
import com.example.doughcalculator.common.extensions.EmptyCallback

interface ErrorAlertShower {

    fun attach(context: Context)

    fun detach()

    fun show(
        info: ExceptionInfo,
        @StringRes okRes: Int = R.string.error_alert_ok_button_ok,
        @StringRes cancelRes: Int? = null,
        @StringRes neutralRes: Int? = null,
        onOkCallback: (EmptyCallback)? = null,
        onNeutralCallback: (EmptyCallback)? = null,
        onCancelCallback: (EmptyCallback)? = null,
        onDismissCallback: (EmptyCallback)? = null,
    )

    fun show(
        @StringRes msgRes: Int,
        @StringRes titleRes: Int = R.string.error_alert_title_error,
        @StringRes okRes: Int = R.string.error_alert_ok_button_ok,
        @StringRes cancelRes: Int? = null,
        @StringRes neutralRes: Int? = null,
        onOkCallback: (EmptyCallback)? = null,
        onNeutralCallback: (EmptyCallback)? = null,
        onCancelCallback: (EmptyCallback)? = null,
        onDismissCallback: (EmptyCallback)? = null,
    )

    fun show(
        msg: String,
        title: String = "Ошибка",
        okButtonText: String = "Ок",
        cancelButtonText: String? = null,
        neutralButtonText: String? = null,
        onOkCallback: (EmptyCallback)? = null,
        onNeutralCallback: (EmptyCallback)? = null,
        onCancelCallback: (EmptyCallback)? = null,
        onDismissCallback: (EmptyCallback)? = null,
    )

    fun show(ex: Exception)
}