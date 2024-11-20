package com.supcoder.apksigner.ui.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.supcoder.apksigner.model.DarkThemeConfig
import com.supcoder.apksigner.vm.MainViewModel

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
    Box(modifier = Modifier
        .height(48.dp)
        .clickable { viewModel.toggleDarkMode(!isDarkMode) }
        .padding(16.dp)
        ) {
        Icon(
            imageVector = if (isDarkMode) Icons.Rounded.DarkMode else Icons.Rounded.LightMode,
            contentDescription = "Dark mode icon",
            modifier = Modifier.size(FilterChipDefaults.IconSize)
        )
    }
}