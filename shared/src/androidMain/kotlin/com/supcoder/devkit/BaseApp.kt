package com.supcoder.devkit

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * BaseApp
 *
 * @author lee
 * @date 2024/11/27
 */
open class BaseApp : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}
