package com.supcoder.apksigner.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import theme.backgroundDark
import theme.backgroundDarkHighContrast
import theme.backgroundDarkMediumContrast
import theme.backgroundLight
import theme.backgroundLightHighContrast
import theme.backgroundLightMediumContrast
import theme.errorContainerDark
import theme.errorContainerDarkHighContrast
import theme.errorContainerDarkMediumContrast
import theme.errorContainerLight
import theme.errorContainerLightHighContrast
import theme.errorContainerLightMediumContrast
import theme.errorDark
import theme.errorDarkHighContrast
import theme.errorDarkMediumContrast
import theme.errorLight
import theme.errorLightHighContrast
import theme.errorLightMediumContrast
import theme.inverseOnSurfaceDark
import theme.inverseOnSurfaceDarkHighContrast
import theme.inverseOnSurfaceDarkMediumContrast
import theme.inverseOnSurfaceLight
import theme.inverseOnSurfaceLightHighContrast
import theme.inverseOnSurfaceLightMediumContrast
import theme.inversePrimaryDark
import theme.inversePrimaryDarkHighContrast
import theme.inversePrimaryDarkMediumContrast
import theme.inversePrimaryLight
import theme.inversePrimaryLightHighContrast
import theme.inversePrimaryLightMediumContrast
import theme.inverseSurfaceDark
import theme.inverseSurfaceDarkHighContrast
import theme.inverseSurfaceDarkMediumContrast
import theme.inverseSurfaceLight
import theme.inverseSurfaceLightHighContrast
import theme.inverseSurfaceLightMediumContrast
import theme.onBackgroundDark
import theme.onBackgroundDarkHighContrast
import theme.onBackgroundDarkMediumContrast
import theme.onBackgroundLight
import theme.onBackgroundLightHighContrast
import theme.onBackgroundLightMediumContrast
import theme.onErrorContainerDark
import theme.onErrorContainerDarkHighContrast
import theme.onErrorContainerDarkMediumContrast
import theme.onErrorContainerLight
import theme.onErrorContainerLightHighContrast
import theme.onErrorContainerLightMediumContrast
import theme.onErrorDark
import theme.onErrorDarkHighContrast
import theme.onErrorDarkMediumContrast
import theme.onErrorLight
import theme.onErrorLightHighContrast
import theme.onErrorLightMediumContrast
import theme.onPrimaryContainerDark
import theme.onPrimaryContainerDarkHighContrast
import theme.onPrimaryContainerDarkMediumContrast
import theme.onPrimaryContainerLight
import theme.onPrimaryContainerLightHighContrast
import theme.onPrimaryContainerLightMediumContrast
import theme.onPrimaryDark
import theme.onPrimaryDarkHighContrast
import theme.onPrimaryDarkMediumContrast
import theme.onPrimaryLight
import theme.onPrimaryLightHighContrast
import theme.onPrimaryLightMediumContrast
import theme.onSecondaryContainerDark
import theme.onSecondaryContainerDarkHighContrast
import theme.onSecondaryContainerDarkMediumContrast
import theme.onSecondaryContainerLight
import theme.onSecondaryContainerLightHighContrast
import theme.onSecondaryContainerLightMediumContrast
import theme.onSecondaryDark
import theme.onSecondaryDarkHighContrast
import theme.onSecondaryDarkMediumContrast
import theme.onSecondaryLight
import theme.onSecondaryLightHighContrast
import theme.onSecondaryLightMediumContrast
import theme.onSurfaceDark
import theme.onSurfaceDarkHighContrast
import theme.onSurfaceDarkMediumContrast
import theme.onSurfaceLight
import theme.onSurfaceLightHighContrast
import theme.onSurfaceLightMediumContrast
import theme.onSurfaceVariantDark
import theme.onSurfaceVariantDarkHighContrast
import theme.onSurfaceVariantDarkMediumContrast
import theme.onSurfaceVariantLight
import theme.onSurfaceVariantLightHighContrast
import theme.onSurfaceVariantLightMediumContrast
import theme.onTertiaryContainerDark
import theme.onTertiaryContainerDarkHighContrast
import theme.onTertiaryContainerDarkMediumContrast
import theme.onTertiaryContainerLight
import theme.onTertiaryContainerLightHighContrast
import theme.onTertiaryContainerLightMediumContrast
import theme.onTertiaryDark
import theme.onTertiaryDarkHighContrast
import theme.onTertiaryDarkMediumContrast
import theme.onTertiaryLight
import theme.onTertiaryLightHighContrast
import theme.onTertiaryLightMediumContrast
import theme.outlineDark
import theme.outlineDarkHighContrast
import theme.outlineDarkMediumContrast
import theme.outlineLight
import theme.outlineLightHighContrast
import theme.outlineLightMediumContrast
import theme.outlineVariantDark
import theme.outlineVariantDarkHighContrast
import theme.outlineVariantDarkMediumContrast
import theme.outlineVariantLight
import theme.outlineVariantLightHighContrast
import theme.outlineVariantLightMediumContrast
import theme.primaryContainerDark
import theme.primaryContainerDarkHighContrast
import theme.primaryContainerDarkMediumContrast
import theme.primaryContainerLight
import theme.primaryContainerLightHighContrast
import theme.primaryContainerLightMediumContrast
import theme.primaryDark
import theme.primaryDarkHighContrast
import theme.primaryDarkMediumContrast
import theme.primaryLight
import theme.primaryLightHighContrast
import theme.primaryLightMediumContrast
import theme.scrimDark
import theme.scrimDarkHighContrast
import theme.scrimDarkMediumContrast
import theme.scrimLight
import theme.scrimLightHighContrast
import theme.scrimLightMediumContrast
import theme.secondaryContainerDark
import theme.secondaryContainerDarkHighContrast
import theme.secondaryContainerDarkMediumContrast
import theme.secondaryContainerLight
import theme.secondaryContainerLightHighContrast
import theme.secondaryContainerLightMediumContrast
import theme.secondaryDark
import theme.secondaryDarkHighContrast
import theme.secondaryDarkMediumContrast
import theme.secondaryLight
import theme.secondaryLightHighContrast
import theme.secondaryLightMediumContrast
import theme.surfaceBrightDark
import theme.surfaceBrightDarkHighContrast
import theme.surfaceBrightDarkMediumContrast
import theme.surfaceBrightLight
import theme.surfaceBrightLightHighContrast
import theme.surfaceBrightLightMediumContrast
import theme.surfaceContainerDark
import theme.surfaceContainerDarkHighContrast
import theme.surfaceContainerDarkMediumContrast
import theme.surfaceContainerHighDark
import theme.surfaceContainerHighDarkHighContrast
import theme.surfaceContainerHighDarkMediumContrast
import theme.surfaceContainerHighLight
import theme.surfaceContainerHighLightHighContrast
import theme.surfaceContainerHighLightMediumContrast
import theme.surfaceContainerHighestDark
import theme.surfaceContainerHighestDarkHighContrast
import theme.surfaceContainerHighestDarkMediumContrast
import theme.surfaceContainerHighestLight
import theme.surfaceContainerHighestLightHighContrast
import theme.surfaceContainerHighestLightMediumContrast
import theme.surfaceContainerLight
import theme.surfaceContainerLightHighContrast
import theme.surfaceContainerLightMediumContrast
import theme.surfaceContainerLowDark
import theme.surfaceContainerLowDarkHighContrast
import theme.surfaceContainerLowDarkMediumContrast
import theme.surfaceContainerLowLight
import theme.surfaceContainerLowLightHighContrast
import theme.surfaceContainerLowLightMediumContrast
import theme.surfaceContainerLowestDark
import theme.surfaceContainerLowestDarkHighContrast
import theme.surfaceContainerLowestDarkMediumContrast
import theme.surfaceContainerLowestLight
import theme.surfaceContainerLowestLightHighContrast
import theme.surfaceContainerLowestLightMediumContrast
import theme.surfaceDark
import theme.surfaceDarkHighContrast
import theme.surfaceDarkMediumContrast
import theme.surfaceDimDark
import theme.surfaceDimDarkHighContrast
import theme.surfaceDimDarkMediumContrast
import theme.surfaceDimLight
import theme.surfaceDimLightHighContrast
import theme.surfaceDimLightMediumContrast
import theme.surfaceLight
import theme.surfaceLightHighContrast
import theme.surfaceLightMediumContrast
import theme.surfaceVariantDark
import theme.surfaceVariantDarkHighContrast
import theme.surfaceVariantDarkMediumContrast
import theme.surfaceVariantLight
import theme.surfaceVariantLightHighContrast
import theme.surfaceVariantLightMediumContrast
import theme.tertiaryContainerDark
import theme.tertiaryContainerDarkHighContrast
import theme.tertiaryContainerDarkMediumContrast
import theme.tertiaryContainerLight
import theme.tertiaryContainerLightHighContrast
import theme.tertiaryContainerLightMediumContrast
import theme.tertiaryDark
import theme.tertiaryDarkHighContrast
import theme.tertiaryDarkMediumContrast
import theme.tertiaryLight
import theme.tertiaryLightHighContrast
import theme.tertiaryLightMediumContrast

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun AppTheme(useDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (!useDarkTheme) {
        lightScheme
    } else {
        darkScheme
    }
    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}