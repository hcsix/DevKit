package com.supcoder.devkit.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.supcoder.devkit.file.FileSelectorType
import kotlinx.coroutines.CoroutineScope
import kotlin.io.path.pathString


/**
 * DropFilePanel
 *
 * @author lee
 * @date 2024/11/25
 */


@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun DropFilePanel(
    scope: CoroutineScope,
    onFileSelector: (String) -> Unit,
    vararg fileSelectorType: FileSelectorType
) {
    var dragging by remember { mutableStateOf(false) }
//    UploadAnimate(dragging, scope)
    Box(
        modifier = Modifier.fillMaxSize()
            .dragAndDropTarget(
                shouldStartDragAndDrop = accept@{ true },
                target = dragAndDropTarget(dragging = {
                    dragging = it
                }, onFinish = { result ->
                    result.onSuccess { fileList ->
                        fileList.firstOrNull()?.let {
                            val path = it.toAbsolutePath().pathString
                            onFileSelector(path)
                        }
                    }
                })
            )
    ) {
        Box(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            FileButton(
                value = if (dragging) {
                    "愣着干嘛，还不松手"
                } else {
                    "点击选择或拖拽上传APK"
                }, expanded = true, *fileSelectorType
            ) { path ->
                onFileSelector(path)
            }
        }
    }
}
