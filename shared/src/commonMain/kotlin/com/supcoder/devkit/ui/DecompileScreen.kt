package com.supcoder.devkit.ui

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
import com.supcoder.devkit.file.FileSelectorType
import com.supcoder.devkit.manager.NurseManager
import com.supcoder.devkit.model.EditorType
import com.supcoder.devkit.model.ThemeConfig
import com.supcoder.devkit.theme.editorBackground
import com.supcoder.devkit.theme.editorBarBackground
import com.supcoder.devkit.theme.editorRootBackground
import com.supcoder.devkit.ui.component.DropFilePanel
import com.supcoder.devkit.ui.decompile.panel.EditPanel
import com.supcoder.devkit.ui.decompile.panel.ImagePanel
import com.supcoder.devkit.ui.decompile.panel.ProjectPanel
import com.supcoder.devkit.ui.decompile.tab.BottomBar
import com.supcoder.devkit.ui.decompile.tab.EditorTitleTab
import com.supcoder.devkit.ui.decompile.tab.LeftBar
import com.supcoder.devkit.ui.decompile.tab.RightBar
import com.supcoder.devkit.util.logger
import com.supcoder.devkit.vm.DragViewModel
import com.supcoder.devkit.vm.EditorViewModel
import com.supcoder.devkit.vm.LeftBarViewModel
import com.supcoder.devkit.vm.MainViewModel

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
