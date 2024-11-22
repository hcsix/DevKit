package com.supcoder.apksigner.ui.decompile.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.supcoder.apksigner.model.action.LeftBarAction
import com.supcoder.apksigner.model.state.LeftBarState
import com.supcoder.apksigner.theme.coderBarColor
import com.supcoder.apksigner.theme.size_coder_bar
import com.supcoder.apksigner.theme.size_coder_bottom_bar


@Composable
fun BottomBar(
    isDark: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(size_coder_bottom_bar)
            .background(color = coderBarColor(isDark))
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
            .background(color = coderBarColor(isDark = isDark))
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
            .background(color = coderBarColor(isDark = isDark))
    ) {

    }
}