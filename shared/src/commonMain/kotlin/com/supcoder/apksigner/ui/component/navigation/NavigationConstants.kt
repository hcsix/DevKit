package com.supcoder.apksigner.ui.component.navigation

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * NavgationConstants
 *
 * @author lee
 * @date 2024/11/22
 */
object NavigationConstants {
    val NavigationRailItemHeight = 56.0.dp


    val NavigationRailItemWidth = 80.0.dp
    val NavigationRailItemWidth_COLLAPSED = 42.0.dp

    fun navigationRailItemWidth(isCollapsed: Boolean = false) =
        if (isCollapsed) NavigationRailItemWidth_COLLAPSED else NavigationRailItemWidth

    val ActiveIndicatorWidth = 56.0.dp
    val ActiveIndicatorWidth_COLLAPSED = 32.0.dp
    fun activeIndicatorWidth(isCollapsed: Boolean = false) =
        if (isCollapsed) ActiveIndicatorWidth_COLLAPSED else ActiveIndicatorWidth

    val ActiveIndicatorHeight = 32.0.dp
    val NoLabelActiveIndicatorHeight = 56.0.dp

    val IconSize = 24.0.dp
    val IconSize_COLLAPSED = 16.0.dp
    fun iconSize(isCollapsed: Boolean) = if (isCollapsed) IconSize_COLLAPSED else IconSize

    val NavigationRailItemVerticalPadding: Dp = 4.dp
    val ItemAnimationDurationMillis: Int = 150

    val IndicatorHorizontalPadding: Dp =
        (ActiveIndicatorWidth - IconSize) / 2
    val IndicatorHorizontalPadding_COLLAPSED: Dp =
        (ActiveIndicatorWidth_COLLAPSED - IconSize_COLLAPSED) / 2

    fun indicatorHorizontalPadding(isCollapsed: Boolean) =
        if (isCollapsed) IndicatorHorizontalPadding_COLLAPSED else IndicatorHorizontalPadding


    val IndicatorVerticalPaddingWithLabel: Dp =
        (ActiveIndicatorHeight - IconSize) / 2

    val IndicatorVerticalPaddingNoLabel: Dp =
        (NoLabelActiveIndicatorHeight - IconSize) / 2


    const val IndicatorRippleLayoutIdTag: String = "indicatorRipple"

    const val IndicatorLayoutIdTag: String = "indicator"

    const val IconLayoutIdTag: String = "icon"

    const val LabelLayoutIdTag: String = "label"

}



