package com.supcoder.apksigner.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest

/**
 * NavigationItem
 *
 * @author lee
 * @date 2024/11/22
 */
@Composable
fun NavigationItem(
    label: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit,
    alwaysShowLabel: Boolean,
    isCollapsed: Boolean = false,
    modifier: Modifier = Modifier
) {


    Box(
        modifier = modifier
            .padding(horizontal = 2.dp, vertical = 10.dp) // 自定义水平内边距
            .padding(2.dp) // 内边距
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val shape = RoundedCornerShape(15.dp)
            val interactionSource = remember { MutableInteractionSource() }
            val isHovered = remember { mutableStateOf(false) }
            val backgroundColor = when {
                selected -> MaterialTheme.colorScheme.secondaryContainer
                isHovered.value -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f)
                else -> Color.Transparent
            }
            LaunchedEffect(interactionSource) {
                interactionSource.interactions.collectLatest { interaction ->
                    when (interaction) {
                        is HoverInteraction.Enter -> isHovered.value = true
                        is HoverInteraction.Exit -> isHovered.value = false
                        else -> {}
                    }
                }
            }
            Box(
                modifier = Modifier
                    .clickable(
                        onClick = onClick,
                        interactionSource = interactionSource,
                        indication = null
                    )
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = backgroundColor,
                        shape = shape
                    )
                    .padding(if (isCollapsed) 2.dp else 10.dp, 2.dp)

            ) {
                icon()
            }
            if (!alwaysShowLabel && selected || alwaysShowLabel) {
                label()
            }
        }
    }
}
