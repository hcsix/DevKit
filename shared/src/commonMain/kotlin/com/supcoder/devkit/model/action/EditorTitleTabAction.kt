package com.supcoder.devkit.model.action

import androidx.compose.runtime.Stable
import com.supcoder.devkit.model.FileItemInfo

/**
 * 编辑器标题栏的相关事件
 */
@Stable
data class EditorTitleTabAction(
    val onClick: (FileItemInfo) -> Unit,
    val onCloseClick: (FileItemInfo) -> Unit
)
