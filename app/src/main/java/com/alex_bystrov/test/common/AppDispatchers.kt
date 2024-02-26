package com.alex_bystrov.test.common

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

data class AppDispatchers(
    val main: CoroutineContext = Dispatchers.Main,
    val io: CoroutineContext = Dispatchers.IO,
    val default: CoroutineContext = Dispatchers.Default,
    val unconfined: CoroutineContext = Dispatchers.Unconfined
)
