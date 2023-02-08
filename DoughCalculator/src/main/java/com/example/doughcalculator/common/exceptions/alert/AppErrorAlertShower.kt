package com.example.doughcalculator.common.exceptions.alert

import android.content.Context
import com.example.doughcalculator.R
import com.example.doughcalculator.common.exceptions.ExceptionInfo
import com.example.doughcalculator.common.extensions.EmptyCallback
import com.example.doughcalculator.common.extensions.showErrorAlertDialog
import java.lang.ref.WeakReference

class AppErrorAlertShower : ErrorAlertShower {

    private var contextRef: WeakReference<Context>? = null

    override fun attach(context: Context) {
        contextRef = WeakReference(context)
    }

    override fun detach() {
        contextRef?.clear()
        contextRef = null
    }

    override fun show(
        info: ExceptionInfo,
        okRes: Int,
        cancelRes: Int?,
        neutralRes: Int?,
        onOkCallback: EmptyCallback?,
        onNeutralCallback: EmptyCallback?,
        onCancelCallback: EmptyCallback?,
        onDismissCallback: EmptyCallback?,
    ) {
        contextRef?.get()?.showErrorAlertDialog(
            info,
            okRes,
            cancelRes,
            neutralRes,
            onOkCallback,
            onNeutralCallback,
            onCancelCallback,
            onDismissCallback,
        )
    }

    override fun show(
        msgRes: Int,
        titleRes: Int,
        okRes: Int,
        cancelRes: Int?,
        neutralRes: Int?,
        onOkCallback: (() -> Unit)?,
        onNeutralCallback: (() -> Unit)?,
        onCancelCallback: (() -> Unit)?,
        onDismissCallback: EmptyCallback?,
    ) {
        contextRef?.get()?.showErrorAlertDialog(
            msgRes,
            titleRes,
            okRes,
            cancelRes,
            neutralRes,
            onOkCallback,
            onNeutralCallback,
            onCancelCallback,
            onDismissCallback,
        )
    }

    override fun show(
        msg: String,
        title: String,
        okButtonText: String,
        cancelButtonText: String?,
        neutralButtonText: String?,
        onOkCallback: (() -> Unit)?,
        onNeutralCallback: (() -> Unit)?,
        onCancelCallback: (() -> Unit)?,
        onDismissCallback: EmptyCallback?,
    ) {
        contextRef?.get()?.showErrorAlertDialog(
            msg,
            title,
            okButtonText,
            cancelButtonText,
            neutralButtonText,
            onOkCallback,
            onNeutralCallback,
            onCancelCallback,
            onDismissCallback,
        )
    }

    override fun show(ex: Exception) {
        show(ExceptionInfo(
            ex,
            messageResId = R.string.error_alert_description_try_again,
            titleResId = R.string.unknown_exception_title
        ))
    }
}