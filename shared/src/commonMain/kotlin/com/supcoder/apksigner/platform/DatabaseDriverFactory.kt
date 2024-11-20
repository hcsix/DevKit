package com.supcoder.apksigner.platform

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.Dispatchers


@OptIn(ExperimentalSettingsApi::class)
fun createFlowSettings() = PreferencesSettings.Factory().create("apkKit").toFlowSettings(Dispatchers.IO)