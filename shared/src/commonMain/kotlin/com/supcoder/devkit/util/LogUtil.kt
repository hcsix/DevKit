package com.supcoder.devkit.util

fun logger(msg: String) {
    println("${loggerTimeMillis()} ${Thread.currentThread().name} --- $msg")
}