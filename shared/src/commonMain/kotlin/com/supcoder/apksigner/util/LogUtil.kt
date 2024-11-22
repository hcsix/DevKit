package com.supcoder.apksigner.util

fun logger(msg: String) {
    println("${loggerTimeMillis()} ${Thread.currentThread().name} --- $msg")
}