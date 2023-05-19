package com.example.doughcalculator.common.mvp

import com.example.doughcalculator.common.exceptions.ExceptionInfo
import com.example.doughcalculator.common.exceptions.alert.ErrorAlertShower
import com.example.doughcalculator.common.exceptions.createErrorHandler
import com.example.doughcalculator.common.extensions.launchUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import moxy.MvpPresenter
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BasePresenter<View : BaseView> : MvpPresenter<View>(), CoroutineScope, KoinComponent {

    private val job = SupervisorJob()
    override val coroutineContext = Dispatchers.Main + job

    private val errorAlertShower: ErrorAlertShower by inject()
    private val exceptionInfoList = mutableListOf<ExceptionInfo>()

    protected fun createAlertErrorHandler(
        callback: ((ExceptionInfo) -> Unit)? = null,
    ) = createErrorHandler {
        showErrorAlert(it, callback)
    }

    protected fun createAlertErrorHandlerUI(
        callback: ((ExceptionInfo) -> Unit)? = null,
    ) = createErrorHandler {
        // in case parent coroutine is not in main thread
        launchUI { showErrorAlert(it, callback) }
    }

    private fun showErrorAlert(
        it: ExceptionInfo,
        callback: ((ExceptionInfo) -> Unit)?,
    ) {
        if (!exceptionInfoList.contains(it)) {
            errorAlertShower.show(it, onDismissCallback = { exceptionInfoList.remove(it) })
            exceptionInfoList.add(it)
        }
        callback?.invoke(it)
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}