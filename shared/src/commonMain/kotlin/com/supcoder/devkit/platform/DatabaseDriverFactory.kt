package com.supcoder.devkit.platform

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.Dispatchers


@OptIn(ExperimentalSettingsApi::class)
expect fun createFlowSettings(): FlowSettings