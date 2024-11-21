package com.supcoder.apksigner.ui.component


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LooksOne
import androidx.compose.material.icons.filled.LooksTwo
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * ExpandCollapseButton
 *
 * @author lee
 * @date 2024/11/21
 */
@Composable
fun ExpandCollapseButton(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onExpandCollapse: (Boolean) -> Unit
) {
    val icon = if (isExpanded) Icons.Filled.LooksTwo else Icons.Filled.LooksOne

    Box(
        modifier = modifier
            .clickable {
                onExpandCollapse(!isExpanded)
            }
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
            .padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = if (isExpanded) "收起" else "展开",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}