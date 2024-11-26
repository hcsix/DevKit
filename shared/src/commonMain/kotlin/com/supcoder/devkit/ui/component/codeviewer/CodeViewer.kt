package com.supcoder.devkit.ui.component.codeviewer

import com.supcoder.devkit.ui.component.codeviewer.common.Settings
import com.supcoder.devkit.ui.component.codeviewer.editor.Editors
import com.supcoder.devkit.ui.component.codeviewer.filetree.FileTree

class CodeViewer(
    val editors: Editors,
    val fileTree: FileTree,
    val settings: Settings
)