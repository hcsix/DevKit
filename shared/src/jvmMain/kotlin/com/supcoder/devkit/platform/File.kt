@file:Suppress("NewApi")

package com.supcoder.devkit.platform
import com.supcoder.devkit.toProjectFile

actual val HomeFolder: File get() = java.io.File(System.getProperty("user.home")).toProjectFile()