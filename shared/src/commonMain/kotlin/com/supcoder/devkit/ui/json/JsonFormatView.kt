package com.supcoder.devkit.ui.json

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.supcoder.devkit.ui.component.ExpandCollapseButton
import com.supcoder.devkit.util.copy
import com.supcoder.devkit.vm.JsonViewModel
import com.supcoder.devkit.vm.MainViewModel

/**
 * JsonFormat
 *
 * @author lee
 * @date 2024/11/21
 */

@Composable
fun JsonFormatView(mainViewModel: MainViewModel) {

    var isExpanded by remember { mutableStateOf(false) }

    val viewModel = viewModel { JsonViewModel() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 12.dp, 16.dp, 12.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
            val content: @Composable (Boolean) -> Unit = {
                if (isExpanded) {
                    JsonFormatTwoPanel(mainViewModel, viewModel)
                } else {
                    JsonFormatSinglePanel(mainViewModel, viewModel)
                }
            }
            Crossfade(
                targetState = isExpanded, modifier = Modifier.fillMaxSize(), content = content
            )
        }

        Box(modifier = Modifier.padding(0.dp,2.dp, 0.dp, 0.dp).fillMaxWidth().wrapContentHeight()) {

            ExpandCollapseButton(
                modifier = Modifier.align(Alignment.CenterStart),
                isExpanded,
                onExpandCollapse = { it -> isExpanded = it })

            // 下半部分：转换按钮
            Button(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = {
                    viewModel.convertJson(isExpanded)
                },
            ) {
                Text("格式化")
            }
        }
    }
}

@Composable
fun JsonFormatSinglePanel(mainViewModel: MainViewModel, jsonViewModel: JsonViewModel) {
    // 上半部分：左右两侧布局
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        // 右侧：格式化后的 JSON 展示
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 2.dp)
        ) {
            TextField(
                value = jsonViewModel.singlePanelJson,
                onValueChange = { jsonViewModel.updateSinglePanelJson(it) },
                label = { Text("JSON 内容") },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White, RoundedCornerShape(2.dp))
                    .padding(2.dp)
            )


            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .height(24.dp)
                    .width(24.dp)
                    .clip(CircleShape).align(Alignment.TopEnd)
                    .clickable(onClick = { // 复制格式化后的 JSON 到剪贴板
                        copy(jsonViewModel.singlePanelJson, mainViewModel)
                    })
            ) {
                Icon(
                    imageVector = Icons.Rounded.ContentCopy,
                    contentDescription = "Copy",
                    modifier = Modifier.clip(CircleShape).fillMaxHeight().fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun JsonFormatTwoPanel(mainViewModel: MainViewModel, jsonViewModel: JsonViewModel) {
    // 上半部分：左右两侧布局
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        // 左侧：输入框
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 2.dp)
        ) {
            TextField(
                value = jsonViewModel.rawJson,
                onValueChange = { jsonViewModel.updateRawJson(it) },
                label = { Text("原始 JSON") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.LightGray, RoundedCornerShape(2.dp))
                    .padding(2.dp)
            )
        }

        // 右侧：格式化后的 JSON 展示
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        ) {
            TextField(
                value = jsonViewModel.formattedJson,
                onValueChange = { /* 不允许编辑 */ },
                label = { Text("格式化后的 JSON") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White, RoundedCornerShape(2.dp))
                    .padding(2.dp)
            )


            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .height(24.dp)
                    .width(24.dp)
                    .clip(CircleShape).align(Alignment.TopEnd)
                    .clickable(onClick = { // 复制格式化后的 JSON 到剪贴板
                        copy(jsonViewModel.formattedJson, mainViewModel)
                    })
            ) {
                Icon(
                    imageVector = Icons.Rounded.ContentCopy,
                    contentDescription = "Copy",
                    modifier = Modifier.clip(CircleShape).fillMaxHeight().fillMaxWidth()
                )
            }
        }
    }
}