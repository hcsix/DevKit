package com.supcoder.devkit.util

import com.supcoder.devkit.vm.MainViewModel
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection

/**
 * @Author      : LazyIonEs
 * @CreateDate  : 2024/2/3 15:56
 * @Description : 描述
 * @Version     : 1.0
 */

fun copy(value: String, viewModel: MainViewModel) {
    copy(value)
    viewModel.updateSnackbarVisuals("已复制到剪切板")
}

/**
 * 复制到剪切板
 */
private fun copy(value: String) {
    val stringSelection = StringSelection(value)
    val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
    clipboard.setContents(stringSelection, null)
}