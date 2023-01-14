package com.example.doughcalculator.common.extensions

import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.doughcalculator.R
import com.example.doughcalculator.common.exceptions.ExceptionInfo
import com.example.doughcalculator.MainActivity.Companion.FIRE_BUTTON_KEY_CODE


fun Context.showErrorAlertDialog(
    exceptionInfo: ExceptionInfo,
    @StringRes okRes: Int = R.string.error_alert_ok_button_ok,
    @StringRes cancelRes: Int? = null,
    @StringRes neutralRes: Int? = null,
    onOkCallback: (EmptyCallback)? = null,
    onNeutralCallback: (EmptyCallback)? = null,
    onCancelCallback: (EmptyCallback)? = null,
    onDismissCallback: (EmptyCallback)? = null,
) {
    showErrorAlertDialog(
        msg = exceptionInfo.getMessage(this),
        title = exceptionInfo.getTitle(this),
        getString(okRes),
        cancelRes?.let { getString(it) },
        neutralRes?.let { getString(it) },
        onOkCallback,
        onNeutralCallback,
        onCancelCallback,
        onDismissCallback,
    )
}

fun Context.showErrorAlertDialog(
    @StringRes msgRes: Int,
    @StringRes titleRes: Int = R.string.error_alert_title_error,
    @StringRes okRes: Int = R.string.error_alert_ok_button_ok,
    @StringRes cancelRes: Int? = null,
    @StringRes neutralRes: Int? = null,
    onOkCallback: (EmptyCallback)? = null,
    onNeutralCallback: (EmptyCallback)? = null,
    onCancelCallback: (EmptyCallback)? = null,
    onDismissCallback: (EmptyCallback)? = null,
) {
    val msg = getString(msgRes)
    val title = getString(titleRes)
    showErrorAlertDialog(
        msg,
        title,
        getString(okRes),
        cancelRes?.let { getString(it) },
        neutralRes?.let { getString(it) },
        onOkCallback,
        onNeutralCallback,
        onCancelCallback,
        onDismissCallback,
    )
}

fun Context.showErrorAlertDialog(
    msg: CharSequence,
    title: String,
    okButtonText: String = "Ок",
    cancelButtonText: String? = null,
    neutralButtonText: String? = null,
    onOkCallback: (EmptyCallback)? = null,
    onNeutralCallback: (EmptyCallback)? = null,
    onCancelCallback: (EmptyCallback)? = null,
    onDismissCallback: (EmptyCallback)? = null,
) {
    MaterialAlertDialogBuilder(this, R.style.WhiteDialogStyle).apply {
        setTitle(title)
        setMessage(msg)
        setPositiveButton(okButtonText) { dialog, _ ->
            onOkCallback?.invoke()
            dialog.dismiss()
        }
        setOnKeyListener { dialog, keyCode, _ -> onKeyPressed(dialog, keyCode) }
        cancelButtonText?.let {
            setNegativeButton(it) { dialog, _ ->
                onCancelCallback?.invoke()
                dialog.dismiss()
            }
        }
        neutralButtonText?.let {
            setNeutralButton(it) { dialog, _ ->
                onNeutralCallback?.invoke()
                dialog.dismiss()
            }
        }
        setOnDismissListener {
            onDismissCallback?.invoke()
        }
        show()
    }
}

private fun onKeyPressed(dialog: DialogInterface, keyCode: Int): Boolean {
    if (keyCode == FIRE_BUTTON_KEY_CODE) dialog.dismiss()
    return true
}

fun Context.showAlertDialog(
    @StringRes
    titleRes: Int? = null,
    title: String? = null,
    @StringRes
    msgRes: Int? = null,
    msg: String? = null,
    @StringRes
    okRes: Int = android.R.string.ok,
    @StringRes
    cancelRes: Int? = android.R.string.cancel,
    okCallback: (() -> Unit)? = null,
) {
    MaterialAlertDialogBuilder(this, R.style.WhiteDialogStyle)
        .setPositiveButton(okRes) { dialog, _ ->
            okCallback?.invoke()
            dialog.dismiss()
        }.apply {
            titleRes?.let { setTitle(it) }
            title?.let { setTitle(it) }
            msgRes?.let { setMessage(it) }
            msg?.let { setMessage(it) }
            cancelRes?.let { setNegativeButton(it) { dialog, _ -> dialog.dismiss() } }
            setOnKeyListener { dialog, keyCode, _ ->
                onExitConfirmKeyPressed(
                    okCallback,
                    dialog,
                    keyCode
                )
            }
            show()
        }
}

private fun onExitConfirmKeyPressed(
    okCallback: (() -> Unit)?,
    dialog: DialogInterface,
    keyCode: Int,
): Boolean {
    if (keyCode == FIRE_BUTTON_KEY_CODE) {
        okCallback?.invoke()
        dialog.dismiss()
    }
    return true
}
