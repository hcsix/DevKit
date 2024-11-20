package com.supcoder.apksigner.i18n

//import androidx.compose.ui.text.intl.Locale
//import java.util.Locale
import java.util.ResourceBundle
import java.util.concurrent.locks.ReentrantLock

object I18n {

    private var currentLanguage: Language = Language.ZH

    private var stringsResCache: ResourceBundle? = null

    private val lock = ReentrantLock()

    init {
//        val currentLocale = Locale.current
//        val currentLocale = Locale.getDefault()
//        println("currentLocale: $currentLocale")
        currentLanguage =
//            when (currentLocale.language) {
            when ("zh") {
                "zh" -> Language.ZH
                else -> Language.EN
            }
    }


    fun changeLanguage(language: Language): Boolean {
        return if (lock.tryLock()) {
            currentLanguage = language
            stringsResCache = null
            true
        } else false
    }

    fun getStringRes(key: String): String {
        lock.lock()
        return try {
            if (stringsResCache == null) {
                val currentLocale = when (currentLanguage) {
                    Language.ZH -> java.util.Locale.CHINA
                    else -> java.util.Locale.ENGLISH
                }
                stringsResCache = ResourceBundle.getBundle("strings", currentLocale)
            }
            stringsResCache?.getString(key) ?: ""
        } finally {
            lock.unlock()
        }
    }
}