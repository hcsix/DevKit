package com.supcoder.devkit.util

import java.awt.FileDialog
import java.io.File
import java.io.IOException
import javax.swing.JFrame

object FileUtils {

    fun selectFile(hints: String, checkFile: (String) -> Boolean, setPath: (String) -> Unit) {
        val fileDialog = FileDialog(JFrame(), hints, FileDialog.LOAD)
        fileDialog.isVisible = true
        val selectedFilePath = fileDialog.file

        if (selectedFilePath != null) {
            try {
                val selectedFile = File(selectedFilePath).canonicalFile
                if (selectedFile.exists() && selectedFile.canRead()) {
                    if (checkFile(selectedFile.absolutePath)) {
                        // 执行文件操作
                        setPath(selectedFile.absolutePath)
                    }
                } else {
                    // 处理文件不存在或无法读取的情况
                    println("选择的文件不存在或无法读取")
                }
            } catch (e: IOException) {
                // 处理路径规范化时的异常
                println("文件路径规范化失败: ${e.message}")
            }
        } else {
            // 处理用户关闭文件选择对话框的情况
            println("用户取消了文件选择")
        }
    }
}