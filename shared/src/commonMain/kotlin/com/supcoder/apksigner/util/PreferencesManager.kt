package com.supcoder.apksigner.util

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.Properties

class PreferencesManager {
    private val properties = Properties()
    private val prefFile = "preferences.properties"

    init {
        loadPreferences()
    }

    private fun loadPreferences() {
        try {
            FileInputStream(prefFile).use { fis ->
                properties.load(fis)
            }
        } catch (e: IOException) {
            // 处理异常
            e.printStackTrace()
        }
    }

    private fun savePreferences() {
        try {
            FileOutputStream(prefFile).use { fos ->
                properties.store(fos, null)
            }
        } catch (e: IOException) {
            // 处理异常
            e.printStackTrace()
        }
    }

    fun setProperty(key: String, value: String) {
        properties.setProperty(key, value)
        savePreferences()
    }

    fun getProperty(key: String): String? {
        return properties.getProperty(key)
    }
}
