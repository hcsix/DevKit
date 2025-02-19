package com.supcoder.devkit.ui.decompile.panel

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowCircleRight
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.material.icons.rounded.Javascript
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supcoder.devkit.manager.NurseManager
import com.supcoder.devkit.model.FileItemInfo
import com.supcoder.devkit.model.ProjectTreeType
import com.supcoder.devkit.model.action.ProjectPanelAction
import com.supcoder.devkit.model.state.ProjectPanelState
import com.supcoder.devkit.theme.editorBarBackground
import com.supcoder.devkit.ui.decompile.scroll.ScrollPanel

/**
 * Project 面板相关UI
 */

@Composable
fun ProjectPanel(
    isDark: Boolean,
    modifier: Modifier = Modifier,
    projectPanelAction: ProjectPanelAction = NurseManager.projectPanelViewModel.projectPanelAction,
    projectPanelState: ProjectPanelState = NurseManager.projectPanelViewModel.projectPanelState.value
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
                .height(40.dp)
                .background(color = editorBarBackground(isDark = isDark))
                .clickable { projectPanelAction.onProjectTreeTypeClick(projectPanelState.projectTreeType) }
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = when (projectPanelState.projectTreeType) {
                    is ProjectTreeType.PROJECT -> {
                        (projectPanelState.projectTreeType as ProjectTreeType.PROJECT).name
                    }

                    is ProjectTreeType.PACKAGES -> {
                        (projectPanelState.projectTreeType as ProjectTreeType.PACKAGES).name
                    }
                },
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
            )

            Icon(
                imageVector = Icons.Rounded.ArrowRight,
                contentDescription = "",
                modifier = Modifier.size(10.dp)
                    .rotate(
                        if (projectPanelState.projectTreeType is ProjectTreeType.PROJECT) {
                            90f
                        } else {
                            0f
                        }
                    )
            )
        }


        val horizontalScrollState = rememberScrollState()
        val verticalScrollState = rememberLazyListState()


        // 工程树结构
        ScrollPanel(
            modifier = Modifier.weight(1f)
                .background(color = editorBarBackground(isDark))
                .padding(2.dp),
            horizontalScrollStateAdapter = rememberScrollbarAdapter(horizontalScrollState),
            verticalScrollStateAdapter = rememberScrollbarAdapter(verticalScrollState)
        ) {
            LazyColumn(
//                verticalArrangement = Arrangement.spacedBy(4.dp), // 设置后会导致VerticalScrollbar的显示异常
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(horizontalScrollState),
                state = verticalScrollState,
            ) {
                itemsIndexed(
                    items = projectPanelState.showedTreeList,
                    key = { index, item ->
                         item.parent + item.name
                    }
                ) { index, item ->
                    ProjectItem(
                        item = item,
                        onClick = {
                            projectPanelAction.onFileItemClick(it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProjectItem(
    item: FileItemInfo,
    onClick: (FileItemInfo) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .height(24.dp)
            .widthIn(min = 300.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick(item) }
    ) {

        Spacer(modifier = Modifier.width((item.depth * 16).dp))

        // 文件夹才有箭头选项
        if (item.isDir) {
            Icon(
                imageVector = Icons.Rounded.ArrowCircleRight,
                contentDescription = "",
                modifier = Modifier.size(10.dp)
                    .rotate(
                        if (item.selected) {
                            90f
                        } else {
                            0f
                        }
                    )
            )
        }

        Image(
//            painter = painterResource(resourcePath = getResByFileItem(item)),
            imageVector = Icons.Rounded.Javascript,
            contentDescription = "",
            modifier = Modifier.size(16.dp)
        )

        Text(
            text = item.showName.ifEmpty {
                item.name
            },
            style = MaterialTheme.typography.labelSmall,
            fontSize = if (item.isRootFile) {
                16.sp
            } else {
                14.sp
            },
            fontWeight = if (item.isRootFile) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
        )
    }
}