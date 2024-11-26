package com.supcoder.devkit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supcoder.devkit.ui.json.JsonFormatView
import com.supcoder.devkit.ui.json.JsonModelView
import com.supcoder.devkit.vm.MainViewModel


/**
 * JsonScreen
 *
 * @author lee
 * @date 2024/11/21
 */
@Composable
fun JsonScreen(viewModel: MainViewModel,tabIndex: Int) {
    var tabIndex by remember { mutableStateOf(tabIndex) }

    Scaffold(
        topBar = {
            Row(modifier = Modifier.fillMaxWidth().padding(48.dp, 16.dp, 48.dp, 0.dp).height(36.dp)) {
                Spacer(modifier = Modifier.weight(0.3f))
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.weight(1f),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf("格式化", "生成模型").forEachIndexed { index, title ->
                            Tab(
                                modifier = Modifier.weight(1f),
                                selected = tabIndex == index,
                                onClick = {
                                    tabIndex = index
                                    // 这里可以添加导航逻辑
                                    // navController.navigate("route_for_$title")
                                },
                                title = title
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(0.3f))
            }


        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                when (tabIndex) {
                    0 -> JsonFormatView(viewModel)
                    1 -> JsonModelView(viewModel)
                }
            }
        }
    )
}

@Composable
fun Tab(modifier: Modifier, selected: Boolean, onClick: () -> Unit, title: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(if (selected) MaterialTheme.colorScheme.primary else Color.Transparent)
            .clickable(onClick = onClick)
    ) {
        BasicText(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp
            )
        )
    }
}
