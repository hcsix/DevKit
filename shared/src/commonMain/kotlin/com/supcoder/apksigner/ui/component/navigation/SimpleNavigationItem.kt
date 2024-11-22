package com.supcoder.apksigner.ui.component.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirst
import androidx.compose.ui.util.fastFirstOrNull
import kotlin.math.roundToInt

/**
 * SimpleNavigationItem
 *
 * @author lee
 * @date 2024/11/22
 */
@Composable
fun SimpleNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    isCollapsed: Boolean = false,
    interactionSource: MutableInteractionSource? = null,
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val styledIcon =
        @Composable {
            val iconColor by animateColorAsState(
                targetValue = LocalContentColor.current,
                animationSpec = tween(NavigationConstants.ItemAnimationDurationMillis)
            )
            // If there's a label, don't have a11y services repeat the icon description.
            val clearSemantics = label != null && (alwaysShowLabel || selected)
            Box(modifier = if (clearSemantics) Modifier.clearAndSetSemantics {} else Modifier) {
                CompositionLocalProvider(LocalContentColor provides iconColor, content = icon)
            }
        }

    val styledLabel: @Composable (() -> Unit)? =
        label?.let {
            @Composable {
                val style = MaterialTheme.typography.labelMedium
                val textColor by
                animateColorAsState(
                    targetValue = LocalContentColor.current,
                    animationSpec = tween(NavigationConstants.ItemAnimationDurationMillis)
                )
                ProvideContentColorTextStyle(
                    contentColor = textColor,
                    textStyle = style,
                    content = label
                )
            }
        }

    Box(
        modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.Tab,
                interactionSource = interactionSource,
                indication = null,
            )
            .defaultMinSize(minHeight = NavigationConstants.NavigationRailItemHeight)
            .widthIn(min = NavigationConstants.navigationRailItemWidth(isCollapsed)),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = true,
    ) {
        val animationProgress: State<Float> =
            animateFloatAsState(
                targetValue = if (selected) 1f else 0f,
                animationSpec = tween(NavigationConstants.ItemAnimationDurationMillis)
            )

        // The entire item is selectable, but only the indicator pill shows the ripple. To achieve
        // this, we re-map the coordinates of the item's InteractionSource into the coordinates of
        // the indicator.
        val deltaOffset: Offset
        with(LocalDensity.current) {
            val itemWidth = NavigationConstants.navigationRailItemWidth(isCollapsed).roundToPx()
            val indicatorWidth = NavigationConstants.activeIndicatorWidth(isCollapsed).roundToPx()
            deltaOffset = Offset((itemWidth - indicatorWidth).toFloat() / 2, 0f)
        }
        val offsetInteractionSource =
            remember(interactionSource, deltaOffset) {
                MappedInteractionSource(interactionSource, deltaOffset)
            }

        val indicatorShape = RoundedCornerShape(18.dp)

        // The indicator has a width-expansion animation which interferes with the timing of the
        // ripple, which is why they are separate composables
        val indicatorRipple =
            @Composable {
                Box(
                    Modifier.layoutId(NavigationConstants.IndicatorRippleLayoutIdTag)
                        .clip(indicatorShape)
                        .indication(
                            offsetInteractionSource,
                            indication = ripple()
                        )
                )
            }
        val indicator =
            @Composable {
                Box(
                    Modifier.layoutId(NavigationConstants.IndicatorLayoutIdTag)
                        .graphicsLayer { alpha = animationProgress.value }
                        .background(color = MaterialTheme.colorScheme.secondaryContainer, shape = indicatorShape)
                )
            }

        NavigationRailItemLayout(
            indicatorRipple = indicatorRipple,
            indicator = indicator,
            icon = styledIcon,
            label = styledLabel,
            alwaysShowLabel = alwaysShowLabel,
            animationProgress = { animationProgress.value },
            isCollapsed = isCollapsed
        )
    }
}

@Composable
private fun NavigationRailItemLayout(
    indicatorRipple: @Composable () -> Unit,
    indicator: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable (() -> Unit)?,
    alwaysShowLabel: Boolean,
    animationProgress: () -> Float,
    isCollapsed: Boolean = false
) {
    Layout({
        indicatorRipple()
        indicator()

        Box(Modifier.layoutId(NavigationConstants.IconLayoutIdTag)) { icon() }

        if (label != null) {
            Box(
                Modifier.layoutId(NavigationConstants.LabelLayoutIdTag).graphicsLayer {
                    alpha = if (alwaysShowLabel) 1f else animationProgress()
                }
            ) {
                label()
            }
        }
    }) { measurables, constraints ->
        @Suppress("NAME_SHADOWING") val animationProgress = animationProgress()
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val iconPlaceable =
            measurables.fastFirst { it.layoutId == NavigationConstants.IconLayoutIdTag }.measure(looseConstraints)

        val totalIndicatorWidth = iconPlaceable.width + (NavigationConstants.indicatorHorizontalPadding(isCollapsed = isCollapsed) * 2).roundToPx()
        val animatedIndicatorWidth = (totalIndicatorWidth * animationProgress).roundToInt()
        val indicatorVerticalPadding =
            if (label == null) {
                NavigationConstants.IndicatorVerticalPaddingNoLabel
            } else {
                NavigationConstants.IndicatorVerticalPaddingWithLabel
            }
        val indicatorHeight = iconPlaceable.height + (indicatorVerticalPadding * 2).roundToPx()

        val indicatorRipplePlaceable =
            measurables
                .fastFirst { it.layoutId == NavigationConstants.IndicatorRippleLayoutIdTag }
                .measure(Constraints.fixed(width = totalIndicatorWidth, height = indicatorHeight))
        val indicatorPlaceable =
            measurables
                .fastFirstOrNull { it.layoutId == NavigationConstants.IndicatorLayoutIdTag }
                ?.measure(
                    Constraints.fixed(width = animatedIndicatorWidth, height = indicatorHeight)
                )

        val labelPlaceable =
            label?.let {
                measurables.fastFirst { it.layoutId == NavigationConstants.LabelLayoutIdTag }.measure(looseConstraints)
            }

        if (label == null) {
            placeIcon(iconPlaceable, indicatorRipplePlaceable, indicatorPlaceable, constraints)
        } else {
            placeLabelAndIcon(
                labelPlaceable!!,
                iconPlaceable,
                indicatorRipplePlaceable,
                indicatorPlaceable,
                constraints,
                alwaysShowLabel,
                animationProgress,
            )
        }
    }
}

private fun MeasureScope.placeIcon(
    iconPlaceable: Placeable,
    indicatorRipplePlaceable: Placeable,
    indicatorPlaceable: Placeable?,
    constraints: Constraints
): MeasureResult {
    val width = constraints.maxWidth
    val height = constraints.constrainHeight(NavigationConstants.NavigationRailItemHeight.roundToPx())

    val iconX = (width - iconPlaceable.width) / 2
    val iconY = (height - iconPlaceable.height) / 2

    val rippleX = (width - indicatorRipplePlaceable.width) / 2
    val rippleY = (height - indicatorRipplePlaceable.height) / 2

    return layout(width, height) {
        indicatorPlaceable?.let {
            val indicatorX = (width - it.width) / 2
            val indicatorY = (height - it.height) / 2
            it.placeRelative(indicatorX, indicatorY)
        }
        iconPlaceable.placeRelative(iconX, iconY)
        indicatorRipplePlaceable.placeRelative(rippleX, rippleY)
    }
}


private fun MeasureScope.placeLabelAndIcon(
    labelPlaceable: Placeable,
    iconPlaceable: Placeable,
    indicatorRipplePlaceable: Placeable,
    indicatorPlaceable: Placeable?,
    constraints: Constraints,
    alwaysShowLabel: Boolean,
    animationProgress: Float,
): MeasureResult {
    val contentHeight =
        iconPlaceable.height +
                NavigationConstants.IndicatorVerticalPaddingWithLabel.toPx() +
                NavigationConstants.NavigationRailItemVerticalPadding.toPx() +
                labelPlaceable.height
    val contentVerticalPadding =
        ((constraints.minHeight - contentHeight) / 2).coerceAtLeast(
            NavigationConstants.IndicatorVerticalPaddingWithLabel.toPx()
        )
    val height = contentHeight + contentVerticalPadding * 2

    // Icon (when selected) should be `contentVerticalPadding` from the top
    val selectedIconY = contentVerticalPadding
    val unselectedIconY =
        if (alwaysShowLabel) selectedIconY else (height - iconPlaceable.height) / 2

    // How far the icon needs to move between unselected and selected states
    val iconDistance = unselectedIconY - selectedIconY

    // The interpolated fraction of iconDistance that all placeables need to move based on
    // animationProgress, since the icon is higher in the selected state.
    val offset = iconDistance * (1 - animationProgress)

    // Label should be fixed padding below icon
    val labelY =
        selectedIconY +
                iconPlaceable.height +
                NavigationConstants.IndicatorVerticalPaddingWithLabel.toPx() +
                NavigationConstants.NavigationRailItemVerticalPadding.toPx()

    val width =
        constraints.constrainWidth(
            maxOf(iconPlaceable.width, labelPlaceable.width, indicatorPlaceable?.width ?: 0)
        )
    val labelX = (width - labelPlaceable.width) / 2
    val iconX = (width - iconPlaceable.width) / 2
    val rippleX = (width - indicatorRipplePlaceable.width) / 2
    val rippleY = selectedIconY - NavigationConstants.IndicatorVerticalPaddingWithLabel.toPx()

    return layout(width, height.roundToInt()) {
        indicatorPlaceable?.let {
            val indicatorX = (width - it.width) / 2
            val indicatorY = selectedIconY - NavigationConstants.IndicatorVerticalPaddingWithLabel.toPx()
            it.placeRelative(indicatorX, (indicatorY + offset).roundToInt())
        }
        if (alwaysShowLabel || animationProgress != 0f) {
            labelPlaceable.placeRelative(labelX, (labelY + offset).roundToInt())
        }
        iconPlaceable.placeRelative(iconX, (selectedIconY + offset).roundToInt())
        indicatorRipplePlaceable.placeRelative(rippleX, (rippleY + offset).roundToInt())
    }
}
