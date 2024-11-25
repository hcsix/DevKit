package com.supcoder.apksigner.ui.json

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supcoder.apksigner.manager.transfer.JavaKeywordVisualTransformation
import com.supcoder.apksigner.manager.transfer.KotlinKeywordVisualTransformation
import com.supcoder.apksigner.model.ThemeConfig
import com.supcoder.apksigner.theme.codeTextColor
import com.supcoder.apksigner.ui.decompile.panel.EditPanel
import com.supcoder.apksigner.util.copy
import com.supcoder.apksigner.vm.MainViewModel

/**
 * JsonKotlinView
 *
 * @author lee
 * @date 2024/11/21
 */
@Composable
fun JsonModelView(mainViewModel: MainViewModel) {

    val rawJson = remember { mutableStateOf("") }
    val generatedCode = remember { mutableStateOf("") }
    val modelLanguage = remember { mutableStateOf("Kotlin") }

    val themeConfig by mainViewModel.themeConfig.collectAsState()
    val isDarkTheme = when (themeConfig) {
        ThemeConfig.LIGHT -> false
        ThemeConfig.DARK -> true
        ThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        else -> false
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 12.dp, 16.dp, 12.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
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
                        value = rawJson.value,
                        textStyle = TextStyle(
                            color = codeTextColor(isDarkTheme),
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily.Monospace
                        ),
                        onValueChange = { newValue ->
                            rawJson.value = newValue
                        },
                        label = { Text("原始 JSON") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.LightGray, RoundedCornerShape(4.dp))
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
                        value = generatedCode.value,
                        onValueChange = { /* 不允许编辑 */ },
                        label = { Text("格式化后的模型") },
                        textStyle = TextStyle(
                            color = codeTextColor(isDarkTheme),
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily.Monospace
                        ),
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(Color.LightGray, RoundedCornerShape(2.dp))
                            .padding(2.dp),
                        visualTransformation = when (modelLanguage.value) {
                            "java" -> {
                                JavaKeywordVisualTransformation
                            }

                            "kotlin" -> {
                                KotlinKeywordVisualTransformation
                            }

                            else -> {
                                VisualTransformation.None
                            }
                        }
                    )

                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .height(24.dp)
                            .width(24.dp)
                            .clip(CircleShape)
                            .align(Alignment.TopEnd)
                            .clickable(onClick = {
                                // 复制格式化后的 JSON 到剪贴板
                                copy(generatedCode.value, mainViewModel)
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

        Box(modifier = Modifier.padding(0.dp, 2.dp, 0.dp, 0.dp).fillMaxWidth().wrapContentHeight()) {

            val options = listOf("Kotlin", "Java")
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.align(Alignment.CenterStart).width(180.dp).height(42.dp)
            ) {
                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                        onClick = {
                            modelLanguage.value = label
                        },
                        selected = modelLanguage.value == label,
                        colors = SegmentedButtonDefaults.colors()
                            .copy(inactiveContainerColor = Color.Transparent)
                    ) {
                        Text(label, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            // 下半部分：转换按钮
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                Button(
                    onClick = {
                        // 转成 Java 代码的逻辑
                        try {
                            when (modelLanguage.value) {
                                "Kotlin" -> generatedCode.value = mainViewModel.convertToJsonKotlin(rawJson.value)
                                "Java" -> generatedCode.value = mainViewModel.convertToJsonJava(rawJson.value)
                            }
                        } catch (e: Exception) {
                            mainViewModel.updateSnackbarVisuals(e.message ?: "")
                        }
                    },
                ) {
                    Text("开始转换")
                }
            }
        }

    }
}
