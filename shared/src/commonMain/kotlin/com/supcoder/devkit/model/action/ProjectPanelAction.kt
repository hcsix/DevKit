package com.supcoder.devkit.model.action

import androidx.compose.runtime.Stable
import com.supcoder.devkit.model.FileItemInfo
import com.supcoder.devkit.model.ProjectTreeType

/**
 * 工程目录面板行为
 */
@Stable
data class ProjectPanelAction(
    val onFileItemClick: (FileItemInfo) -> Unit,
    val onProjectTreeTypeClick: (ProjectTreeType) -> Unit
)
