package com.example.doughcalculator.common.extensions

import kotlinx.coroutines.*

fun CoroutineScope.launchUI(callback: suspend CoroutineScope.() -> Unit) =
    launch(Dispatchers.Main) { callback() }

fun CoroutineScope.launchUI(
    handler: CoroutineExceptionHandler,
    callback: suspend CoroutineScope.() -> Unit,
) =
    launch(Dispatchers.Main + handler) { callback() }

fun CoroutineScope.launchIO(callback: suspend CoroutineScope.() -> Unit) =
    launch(Dispatchers.IO) { callback() }

fun CoroutineScope.launchIO(
    handler: CoroutineExceptionHandler,
    callback: suspend CoroutineScope.() -> Unit,
) = launch(Dispatchers.IO + handler) { callback() }

suspend fun <T> withIO(callback: suspend () -> T) =
    withContext(Dispatchers.IO) { callback() }

suspend fun <T> withUI(callback: suspend () -> T) =
    withContext(Dispatchers.Main) { callback() }

fun <T> CoroutineScope.asyncIO(callback: suspend CoroutineScope.() -> T) =
    async(Dispatchers.IO) { callback() }

suspend fun <T> awaitAllNotNull(vararg deferreds: Deferred<T>?): List<T> =
    deferreds.filterNotNull().awaitAll()