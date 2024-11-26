package com.supcoder.devkit.ui.decompile.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.supcoder.devkit.model.action.LeftBarAction
import com.supcoder.devkit.model.state.LeftBarState
import com.supcoder.devkit.theme.editorBarBackground
import com.supcoder.devkit.theme.size_coder_bar
import com.supcoder.devkit.theme.size_coder_bottom_bar


@Composable
fun BottomBar(
    isDark: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(size_coder_bottom_bar)
            .background(color = editorBarBackground(isDark))
    ) {

    }
}

@Composable
fun LeftBar(
    isDark: Boolean,
    leftBarAction: LeftBarAction,
    leftBarState: LeftBarState
) {
    Column(
        modifier = Modifier.fillMaxHeight()
            .width(size_coder_bar)
            .background(color = editorBarBackground(isDark = isDark))
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BarMenu(
            selected = leftBarState.projectOn,
            onClick = leftBarAction.clickProject,
        )
    }
}

@Composable
fun RightBar(
    isDark: Boolean
) {
    Column(
        modifier = Modifier.fillMaxHeight()
            .width(size_coder_bar)
            .background(color = editorBarBackground(isDark = isDark))
    ) {

    }
}