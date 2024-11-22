package com.supcoder.apksigner.model.state

import androidx.compose.runtime.Stable
import com.supcoder.apksigner.model.FileItemInfo

/**
 * 编辑器标题栏的状态
 */
@Stable
data class EditorTitleTabState(
    val selectedIndex: Int = 0,
    val viewedFileList: List<FileItemInfo> = mutableListOf()
)
