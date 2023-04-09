package com.example.doughcalculator.common.exceptions

import android.content.Context
import com.example.doughcalculator.R

data class ExceptionInfo(
    val throwable: Throwable?,
    val messageResId: Int? = null,
    val message: String? = null,
    val titleResId: Int = R.string.error_alert_title_error,
    val title: String? = null,
) {

    fun getMessage(context: Context) = message ?: context.getString(messageResId!!)

    fun getTitle(context: Context) = title ?: context.getString(titleResId)
}