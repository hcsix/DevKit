package com.supcoder.apksigner.ui.component.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
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

    val ActiveIndicatorWidth = 56.0.dp
    val ActiveIndicatorHeight = 32.0.dp

    val IconSize = 24.0.dp

    val ActiveIndicatorShape = ShapeKeyTokens.CornerFull
    val NoLabelActiveIndicatorShape = ShapeKeyTokens.CornerFull

    val ItemAnimationDurationMillis: Int = 150

    val NoLabelActiveIndicatorHeight = 56.0.dp


    /*@VisibleForTesting*/
    /** Vertical padding between the contents of a [NavigationRailItem] and its top/bottom. */
    val NavigationRailItemVerticalPadding: Dp = 4.dp

    val IndicatorHorizontalPadding: Dp =
        (ActiveIndicatorWidth - IconSize) / 2

    val IndicatorVerticalPaddingWithLabel: Dp =
        (ActiveIndicatorHeight - IconSize) / 2

    val IndicatorVerticalPaddingNoLabel: Dp =
        (NoLabelActiveIndicatorHeight - IconSize) / 2


    const val IndicatorRippleLayoutIdTag: String = "indicatorRipple"

    const val IndicatorLayoutIdTag: String = "indicator"

    const val IconLayoutIdTag: String = "icon"

    const val LabelLayoutIdTag: String = "label"


    var defaultNavigationItemColors: NavigationItemColors? = null

    internal const val DisabledAlpha = 0.38f

    @Composable
    fun colors() = defaultNavigationItemColors ?: NavigationItemColors(
        selectedIconColor = MaterialTheme.colorScheme.secondaryContainer,
        selectedTextColor = MaterialTheme.colorScheme.surface,
        selectedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
        unselectedIconColor = MaterialTheme.colorScheme.surfaceVariant,
        unselectedTextColor = MaterialTheme.colorScheme.surfaceVariant,
        disabledIconColor = MaterialTheme.colorScheme.surfaceVariant
            .copy(alpha = DisabledAlpha),
        disabledTextColor = MaterialTheme.colorScheme.surfaceVariant
            .copy(alpha = DisabledAlpha),
    )
        .also { defaultNavigationItemColors = it }
}



enum class ShapeKeyTokens {
    CornerExtraLarge,
    CornerExtraLargeTop,
    CornerExtraSmall,
    CornerExtraSmallTop,
    CornerFull,
    CornerLarge,
    CornerLargeEnd,
    CornerLargeTop,
    CornerMedium,
    CornerNone,
    CornerSmall,
}

enum class TypographyKeyTokens {
    BodyLarge,
    BodyMedium,
    BodySmall,
    DisplayLarge,
    DisplayMedium,
    DisplaySmall,
    HeadlineLarge,
    HeadlineMedium,
    HeadlineSmall,
    LabelLarge,
    LabelMedium,
    LabelSmall,
    TitleLarge,
    TitleMedium,
    TitleSmall,
}