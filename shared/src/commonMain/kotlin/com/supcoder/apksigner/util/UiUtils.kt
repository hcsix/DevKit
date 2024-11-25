package com.supcoder.apksigner.util

import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerInputScope

/**
 * UiUtils
 *
 * @author lee
 * @date 2024/11/25
 */
suspend fun PointerInputScope.detectMouseMovement(onMove: (Float, Float) -> Unit) {
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent()
            if (event.type == PointerEventType.Move) {
                val changes = event.changes
                if (changes.isNotEmpty()) {
                    val change = changes.first()
                    val x = change.position.x
                    val y = change.position.y
                    onMove(x, y)
                }
            }
        }
    }
}