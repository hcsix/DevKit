package com.supcoder.devkit.util

import java.text.SimpleDateFormat


private val loggerFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
private val fileFormatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS")

/**
 * 格式化当前毫秒时间
 *
 */
fun loggerTimeMillis(): String {
    return loggerFormatter.format(System.currentTimeMillis())
}

/**
 * 格式化当前毫秒时间
 */
fun fileTimeMillis(): String {
    return fileFormatter.format(System.currentTimeMillis())
}

/**
 * 格式化输入的毫秒时间
 */
fun fileTimeMillis(timeMillis: Long): String {
    return fileFormatter.format(timeMillis)
}