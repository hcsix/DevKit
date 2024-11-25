package com.supcoder.apksigner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.supcoder.apksigner.file.FileSelectorType
import com.supcoder.apksigner.manager.NurseManager
import com.supcoder.apksigner.model.EditorType
import com.supcoder.apksigner.model.ThemeConfig
import com.supcoder.apksigner.theme.editorBackground
import com.supcoder.apksigner.theme.editorBarBackground
import com.supcoder.apksigner.theme.editorRootBackground
import com.supcoder.apksigner.ui.component.DropFilePanel
import com.supcoder.apksigner.ui.decompile.panel.EditPanel
import com.supcoder.apksigner.ui.decompile.panel.ImagePanel
import com.supcoder.apksigner.ui.decompile.panel.ProjectPanel
import com.supcoder.apksigner.ui.decompile.tab.BottomBar
import com.supcoder.apksigner.ui.decompile.tab.EditorTitleTab
import com.supcoder.apksigner.ui.decompile.tab.LeftBar
import com.supcoder.apksigner.ui.decompile.tab.RightBar
import com.supcoder.apksigner.util.logger
import com.supcoder.apksigner.vm.DragViewModel
import com.supcoder.apksigner.vm.EditorViewModel
import com.supcoder.apksigner.vm.LeftBarViewModel
import com.supcoder.apksigner.vm.MainViewModel

/**
 * DecompileScreen
 *
 * @author lee
 * @date 2024/11/22
 */
/**
 * 将应用的UI规划为几个大部分
 */
@Composable
fun DecompileScreen(
    mainViewModel: MainViewModel,
    leftBarViewModel: LeftBarViewModel = NurseManager.leftBarViewModel,
    dragViewModel: DragViewModel = NurseManager.dragViewModel,
    editorViewModel: EditorViewModel = NurseManager.editorViewModel
) {
    val themeConfig by mainViewModel.themeConfig.collectAsState()
    val isDarkTheme = when (themeConfig) {
        ThemeConfig.LIGHT -> false
        ThemeConfig.DARK -> true
        ThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        else -> false
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
            .clip(RoundedCornerShape(2.dp))
            .border(width = 1.dp, color = editorRootBackground(isDarkTheme), shape = RoundedCornerShape(2.dp))
            .background(color = editorRootBackground(isDark = isDarkTheme)),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(1.dp)
        ) {

            // 左侧功能栏
            LeftBar(
                isDark = isDarkTheme,
                leftBarAction = leftBarViewModel.leftBarAction,
                leftBarState = leftBarViewModel.leftBarState.value
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {

                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(1.dp)
                ) {

                    // 工程项目结构区域
                    if (leftBarViewModel.leftBarState.value.projectOn) {
                        ProjectPanel(
                            isDarkTheme,
                            modifier = Modifier.width(240.dp)
                        )
                    }
                    when (editorViewModel.editorType.value) {
                        is EditorType.NONE -> {
                            // 拖动文件到此
                            DropFilePanel(
                                scope = scope,
                                onFileSelector = {
                                    if (it.isNotEmpty()) {
                                        logger("onFileSelector:path is $it")
                                    }
                                    dragViewModel.dragAction.onFileDrop(it)
                                },
                                fileSelectorType = arrayOf(
                                    FileSelectorType.APK
                                )
                            )
                        }

                        else -> {

                            Column(modifier = Modifier.fillMaxWidth().weight(1f)) {

                                // 编辑区的标题栏
                                EditorTitleTab(
                                    isDarkTheme,
                                    modifier = Modifier.fillMaxWidth()
                                        .height(40.dp)
                                        .background(color = Color.Transparent),
                                    editorTitleTabState = editorViewModel.editorTitleTabState.value,
                                    editorTitleTabAction = editorViewModel.editorTitleTabAction
                                )

                                Divider(
                                    modifier = Modifier.fillMaxWidth().height(1.dp),
                                    color = editorBarBackground(isDarkTheme)
                                )

                                when (editorViewModel.editorType.value) {
                                    is EditorType.TEXT -> {
                                        // 代码编辑区域
                                        EditPanel(
                                            isDarkTheme,
                                            modifier = Modifier.fillMaxSize()
                                                .weight(1f)
                                                .background(color = editorBackground(isDarkTheme))
                                                .padding(2.dp),
                                            textContent = (editorViewModel.editorType.value as EditorType.TEXT).textContent,
                                            textType = (editorViewModel.editorType.value as EditorType.TEXT).textType,
                                        )
                                    }

                                    is EditorType.IMAGE -> {
                                        // 图片编辑区域
                                        ImagePanel(
                                            modifier = Modifier.fillMaxSize()
                                                .weight(1f)
                                                .background(color = editorBackground(isDarkTheme))
                                                .padding(2.dp),
                                            content = (editorViewModel.editorType.value as EditorType.IMAGE).imagePath
                                        )
                                    }

                                    else -> {

                                    }
                                }

                            }
                        }
                    }
                }
                // 底部的控制台区域


            }

            // 右侧功能栏
            RightBar(isDarkTheme)
        }

        // 底部功能栏
        BottomBar(isDarkTheme)
    }
}
