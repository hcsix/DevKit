package com.supcoder.apksigner.ui.decompile.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.supcoder.apksigner.theme.corner_normal
import com.supcoder.apksigner.theme.size_icon


@Composable
fun BarMenu(
    imageVector: ImageVector = Icons.Rounded.Folder,
    enabled: Boolean = true,
    selected: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier.size(size_icon)
            .clip(RoundedCornerShape(corner_normal))
            .background(
                color = if (selected) {
                    Color(0xff3266cc)
                } else {
                    Color.Transparent
                }
            )
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = ripple()
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.Folder,
            contentDescription = "folder",
            modifier = Modifier.fillMaxSize()
                .padding(4.dp),
        )
    }
}