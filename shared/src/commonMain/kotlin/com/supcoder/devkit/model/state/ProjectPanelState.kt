package com.supcoder.devkit.model.state

import androidx.compose.runtime.Stable
import com.supcoder.devkit.model.FileItemInfo
import com.supcoder.devkit.model.ProjectTreeType

@Stable
data class ProjectPanelState(
    var projectTreeType: ProjectTreeType = ProjectTreeType.PROJECT(),
    var showedTreeList: List<FileItemInfo> = mutableListOf()
)