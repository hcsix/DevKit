package com.supcoder.apksigner.model.state

import androidx.compose.runtime.Stable
import com.supcoder.apksigner.model.FileItemInfo
import com.supcoder.apksigner.model.ProjectTreeType

@Stable
data class ProjectPanelState(
    var projectTreeType: ProjectTreeType = ProjectTreeType.PROJECT(),
    var showedTreeList: List<FileItemInfo> = mutableListOf()
)