package com.supcoder.devkit.ui.decompile.tab

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supcoder.devkit.model.FileItemInfo
import com.supcoder.devkit.model.action.EditorTitleTabAction
import com.supcoder.devkit.model.state.EditorTitleTabState
import com.supcoder.devkit.theme.codeTextColor
import kotlinx.coroutines.launch

/**
 * 编辑区的标题栏
 */
@Composable
fun EditorTitleTab(
    isDark: Boolean,
    modifier: Modifier,
    editorTitleTabState: EditorTitleTabState,
    editorTitleTabAction: EditorTitleTabAction
) {

    val horizontalScrollState = rememberLazyListState()
    val currentCoroutineScope = rememberCoroutineScope()

    SideEffect {
        currentCoroutineScope.launch {
            horizontalScrollState.scrollToItem(editorTitleTabState.selectedIndex)
        }
    }

    LazyRow(
        modifier = modifier
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    currentCoroutineScope.launch {
                        horizontalScrollState.scrollBy(-dragAmount)
                    }
                }
            },
        state = horizontalScrollState
    ) {
        itemsIndexed(items = editorTitleTabState.viewedFileList) { index, item ->
            TitleTabItem(
                isDark = isDark,
                fileItemInfo = item,
                isSelected = index == editorTitleTabState.selectedIndex,
                onClick = {
                    editorTitleTabAction.onClick(item)
                },
                onCloseClick = {
                    editorTitleTabAction.onCloseClick(item)
                }
            )
        }
    }

//    ScrollableTabRow(
//        selectedTabIndex = editorTitleTabState.selectedIndex,
//        modifier = modifier.fillMaxSize()
//            .pointerInput(Unit) {
//                detectHorizontalDragGestures { change, dragAmount ->
//                    logger("检测到了横向 + ${dragAmount.dp}")
//                }
//            },
//        backgroundColor = Color.Transparent,
//        contentColor = Color.Transparent,
//        edgePadding = 0.dp,
//        tabs = {
//            editorTitleTabState.viewedFileList.forEachIndexed { index, fileItemInfo ->
//                TitleTabItem(
//                    fileItemInfo = fileItemInfo,
//                    isSelected = index == editorTitleTabState.selectedIndex,
//                    onClick = editorTitleTabAction.onClick,
//                    onCloseClick = editorTitleTabAction.onCloseClick
//                )
//            }
//        }
//    )

}

@Composable
private fun TitleTabItem(
    isDark: Boolean,
    fileItemInfo: FileItemInfo,
    isSelected: Boolean,
    onClick: () -> Unit = {},
    onCloseClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .width(intrinsicSize = IntrinsicSize.Max)
            .clickable {
                onClick()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
                .wrapContentWidth()
                .widthIn(min = 100.dp, max = 300.dp)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                imageVector = Icons.Rounded.Folder,
//                painter = painterResource(resourcePath = getResByFileItem(fileItemInfo)),
                contentDescription = "",
                modifier = Modifier.size(14.dp)
            )

            Text(
                text = fileItemInfo.name,
                color = if (isSelected) {
                    Color.White
                } else {
                    codeTextColor(isDark = isDark)
                },
                fontSize = 14.sp,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Clip,
                maxLines = 1
            )

            Image(
//                painter = painterResource("icons/icon_tab_close.svg"),
                imageVector = Icons.Filled.Close,
                contentDescription = "",
                modifier = Modifier.size(18.dp)
                    .clickable {
                        onCloseClick()
                    }
                    .padding(4.dp)
            )
        }

        // 指示器
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .clip(RoundedCornerShape(50))
                .background(
                    color = if (isSelected) {
                        Color(0xFF3674f0)
                    } else {
                        Color.Transparent
                    }
                )
                .clickable {
                    onCloseClick()
                }
        )
    }
}