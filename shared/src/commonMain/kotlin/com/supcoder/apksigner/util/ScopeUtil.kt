package com.supcoder.apksigner.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * 创建一个IO Scope
 *
 * 子协程之间互不影响
 */
val ioScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
