package com.supcoder.devkit.platform

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.SharedPreferencesSettings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import com.supcoder.devkit.BaseApp
import kotlinx.coroutines.Dispatchers

/**
 * @Author      : LazyIonEs
 * @CreateDate  : 2024/2/20 17:52
 * @Description : 数据库驱动工厂
 * @Version     : 1.0
 */
@OptIn(ExperimentalSettingsApi::class)
actual fun createFlowSettings(): FlowSettings {
    return SharedPreferencesSettings.Factory(BaseApp.context).create("devkit").toFlowSettings(Dispatchers.IO)
}