package com.supcoder.devkit.ui.component.codeviewer

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.supcoder.devkit.ui.component.codeviewer.common.LocalTheme
import com.supcoder.devkit.ui.component.codeviewer.common.Settings
import com.supcoder.devkit.ui.component.codeviewer.common.Theme
import com.supcoder.devkit.ui.component.codeviewer.editor.Editors
import com.supcoder.devkit.ui.component.codeviewer.filetree.FileTree
import  com.supcoder.devkit.platform.HomeFolder

@Composable
fun MainView() {
    val codeViewer = remember {
        val editors = Editors()

        CodeViewer(
            editors = editors,
            fileTree = FileTree(HomeFolder, editors),
            settings = Settings()
        )
    }

    DisableSelection {
        val theme = if (isSystemInDarkTheme()) Theme.dark else Theme.light
        CompositionLocalProvider(
            LocalTheme provides theme,
        ) {
            MaterialTheme(
                colors = theme.materialColors
            ) {
                Surface {
                    CodeViewerView(codeViewer)
                }
            }
        }
    }
}