package com.supcoder.apksigner.ui.component

import androidx.compose.animation.core.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.supcoder.apksigner.model.DarkThemeConfig
import com.supcoder.apksigner.vm.MainViewModel
import kotlinx.coroutines.launch

/**
 * DarkModeToggleButton
 *
 * @author lee
 * @date 2024/11/20
 */
@Composable
@Preview
fun DarkModeToggleButton(viewModel: MainViewModel) {
    val themeConfig by viewModel.themeConfig.collectAsState()
    val isDarkMode = when (themeConfig) {
        DarkThemeConfig.LIGHT -> false
        DarkThemeConfig.DARK -> true
        DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        else -> false
    }
    var interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val backgroundColor by animateFloatAsState(
        targetValue = if (isHovered) 0.2f else 0f,
        label = "Background Color"
    )
    val iconOffset = remember { Animatable(0f) }
    val isSwitching = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier
        .padding(0.dp, 8.dp)
        .height(48.dp)
        .width(48.dp)
        .clip(CircleShape)
        .border(1.dp, Color.Gray, CircleShape)
        .background(Color.Gray.copy(alpha = backgroundColor))
        .clickable(interactionSource = interactionSource, indication = null) {
            isSwitching.value = true
            coroutineScope.launch {
                val targetOffset = if (!isDarkMode) -48f else 48f
//                iconOffset.snapTo(targetOffset)
                iconOffset.animateTo(
                    targetValue = targetOffset,
                    animationSpec = tween(durationMillis = 300)
                )
                viewModel.toggleDarkMode(!isDarkMode)
                iconOffset.snapTo(if (!isDarkMode) 48f else -48f)
                iconOffset.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 300)
                )
                isSwitching.value = false
            }
        }
        .padding(16.dp)
    ) {
        Icon(
            imageVector = when {
                !isDarkMode && !isHovered -> Icons.Outlined.DarkMode
                !isDarkMode && isHovered -> Icons.Filled.DarkMode
                isDarkMode && !isHovered -> Icons.Outlined.LightMode
                isDarkMode && isHovered -> Icons.Filled.LightMode
                else -> Icons.Outlined.LightMode
            },
            contentDescription = if (isDarkMode) "Dark Mode" else "Light Mode",
            modifier = Modifier.size(24.dp).scale(1.2f) // 放大图标
                 .offset(y = iconOffset.value.dp)
        )
    }
}
