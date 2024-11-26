package com.supcoder.devkit.i18n


import com.supcoder.devkit.util.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LanguageManager {
    private val preferencesManager = PreferencesManager()
    private val _language = MutableStateFlow(preferencesManager.getProperty("language") ?: "en")
    val language: StateFlow<String> get() = _language.asStateFlow()

    fun changeLanguage(newLanguage: String) {
        _language.value = newLanguage
        preferencesManager.setProperty("language", newLanguage)
        I18n.changeLanguage(when (newLanguage) {
            "zh" -> Language.ZH
            else -> Language.EN
        })
    }
}