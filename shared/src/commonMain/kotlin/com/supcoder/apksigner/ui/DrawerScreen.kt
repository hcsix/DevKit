package com.supcoder.apksigner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerState
import androidx.compose.material.ModalDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * DrawerScreen
 *
 * @author lee
 * @date 2024/11/24
 */
@Composable
fun DrawerScreen(drawerState: DrawerState) {
    ModalDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(16.dp)
            ) {
                Text("Drawer Content", fontSize = 24.sp, color = Color.Black)
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(16.dp)
            ) {
                Text("Content", fontSize = 24.sp, color = Color.Black)
            }
        }
    )
}