package com.supcoder.devkit.ui.component.navigation

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.flow.map

/**
 * MappedInteractionSource
 *
 * @author lee
 * @date 2024/11/22
 */
 class MappedInteractionSource(
    underlyingInteractionSource: InteractionSource,
    private val delta: Offset
) : InteractionSource {
    private val mappedPresses = mutableMapOf<PressInteraction.Press, PressInteraction.Press>()

    override val interactions =
        underlyingInteractionSource.interactions.map { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    val mappedPress = mapPress(interaction)
                    mappedPresses[interaction] = mappedPress
                    mappedPress
                }
                is PressInteraction.Cancel -> {
                    val mappedPress = mappedPresses.remove(interaction.press)
                    if (mappedPress == null) {
                        interaction
                    } else {
                        PressInteraction.Cancel(mappedPress)
                    }
                }
                is PressInteraction.Release -> {
                    val mappedPress = mappedPresses.remove(interaction.press)
                    if (mappedPress == null) {
                        interaction
                    } else {
                        PressInteraction.Release(mappedPress)
                    }
                }
                else -> interaction
            }
        }

    private fun mapPress(press: PressInteraction.Press): PressInteraction.Press =
        PressInteraction.Press(press.pressPosition - delta)
}
