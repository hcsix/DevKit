package com.supcoder.apksigner.ui.decompile.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.supcoder.apksigner.model.action.LeftBarAction
import com.supcoder.apksigner.model.state.LeftBarState
import com.supcoder.apksigner.theme.appBarColor
import com.supcoder.apksigner.theme.appBarSize
import com.supcoder.apksigner.theme.appBottomBarSize
import com.supcoder.apksigner.theme.appTopBarColor


@Composable
fun BottomBar() {
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(appBottomBarSize)
            .background(color = appBarColor)
    ) {

    }
}

@Composable
fun LeftBar(
    leftBarAction: LeftBarAction,
    leftBarState: LeftBarState
) {
    Column(
        modifier = Modifier.fillMaxHeight()
            .width(appBarSize)
            .background(color = appBarColor)
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BarMenu(
            iconPath = "icons/bar_left_folder.svg",
            selected = leftBarState.projectOn,
            onClick = leftBarAction.clickProject,
        )
    }
}

@Composable
fun RightBar(

) {
    Column(
        modifier = Modifier.fillMaxHeight()
            .width(appBarSize)
            .background(color = appBarColor)
    ) {

    }
}