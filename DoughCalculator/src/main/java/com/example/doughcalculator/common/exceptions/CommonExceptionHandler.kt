package com.example.doughcalculator.common.exceptions

import com.example.doughcalculator.R
import kotlinx.coroutines.CoroutineExceptionHandler

class CommonExceptionHandler(private val onError: ((ExceptionInfo) -> Unit)? = null) {

    val coroutineHandler = CoroutineExceptionHandler { _, throwable ->
        onError?.let {
            val exceptionInfo = ExceptionInfo(
                throwable,
                messageResId = R.string.error_alert_description_try_again,
                titleResId = R.string.unknown_exception_title
            )
            it.invoke(exceptionInfo)
        }
    }
}

fun createErrorHandler(onError: (ExceptionInfo) -> Unit) =
    CommonExceptionHandler(onError).coroutineHandler

fun createEmptyErrorHandler() = createErrorHandler { /* pass */ }