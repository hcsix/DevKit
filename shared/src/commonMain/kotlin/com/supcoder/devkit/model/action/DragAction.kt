package com.supcoder.devkit.model.action


/**
 * 拖拽文件释放的行为
 */
data class DragAction(
    val onFileDrop: (String) -> Unit
)