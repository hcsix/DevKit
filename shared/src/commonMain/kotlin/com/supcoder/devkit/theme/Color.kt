package com.supcoder.devkit.theme


import androidx.compose.ui.graphics.Color
import kotlin.random.Random

val primaryLight = Color(0xFF6443D6)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFE2E1F9)
val onPrimaryContainerLight = Color(0xFF102000)
val secondaryLight = Color(0xFF586249)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFDCDAF5)
val onSecondaryContainerLight = Color(0xFF151E0B)
val tertiaryLight = Color(0xFF386663)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFFBCECE7)
val onTertiaryContainerLight = Color(0xFF00201E)
val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFFDAD6)
val onErrorContainerLight = Color(0xFF410002)
val backgroundLight = Color(0xFFFEFBFF)
val onBackgroundLight = Color(0xFF1A1C16)
val surfaceLight = Color(0xFFFEFBFF)
val onSurfaceLight = Color(0xFF1A1C16)
val surfaceVariantLight = Color(0xFFF2ECEE)
val onSurfaceVariantLight = Color(0xFF44483D)
val outlineLight = Color(0xFF75796C)
val outlineVariantLight = Color(0xFFC5C8BA)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF2F312A)
val inverseOnSurfaceLight = Color(0xFFF1F2E6)
val inversePrimaryLight = Color(0xFFB1D18A)
val surfaceDimLight = Color(0xFFDADBD0)
val surfaceBrightLight = Color(0xFFFEFBFF)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFF2ECEE)
val surfaceContainerLight = Color(0xFFF8F1F6)
val surfaceContainerHighLight = Color(0xFFE8E9DE)
val surfaceContainerHighestLight = Color(0xFFF8F1F6)



val primaryDark = Color(0xFFB1D18A)
val onPrimaryDark = Color(0xFF1F3701)
val primaryContainerDark = Color(0xFF354E16)
val onPrimaryContainerDark = Color(0xFFF2ECEE)
val secondaryDark = Color(0xFFBFCBAD)
val onSecondaryDark = Color(0xFF2A331E)
val secondaryContainerDark = Color(0xFF404A33)
val onSecondaryContainerDark = Color(0xFFDCDAF5)
val tertiaryDark = Color(0xFFA0D0CB)
val onTertiaryDark = Color(0xFF003735)
val tertiaryContainerDark = Color(0xFF1F4E4B)
val onTertiaryContainerDark = Color(0xFFBCECE7)
val errorDark = Color(0xFFFFB4AB)
val onErrorDark = Color(0xFF690005)
val errorContainerDark = Color(0xFF93000A)
val onErrorContainerDark = Color(0xFFFFDAD6)
val backgroundDark = Color(0xFF12140E)
val onBackgroundDark = Color(0xFFF8F1F6)
val surfaceDark = Color(0xFF12140E)
val onSurfaceDark = Color(0xFFF8F1F6)
val surfaceVariantDark = Color(0xFF44483D)
val onSurfaceVariantDark = Color(0xFFC5C8BA)
val outlineDark = Color(0xFF8F9285)
val outlineVariantDark = Color(0xFF44483D)
val scrimDark = Color(0xFF000000)
val inverseSurfaceDark = Color(0xFFF8F1F6)
val inverseOnSurfaceDark = Color(0xFF2F312A)
val inversePrimaryDark = Color(0xFF6443D6)
val surfaceDimDark = Color(0xFF12140E)
val surfaceBrightDark = Color(0xFF383A32)
val surfaceContainerLowestDark = Color(0xFF0C0F09)
val surfaceContainerLowDark = Color(0xFF1A1C16)
val surfaceContainerDark = Color(0xFF1E201A)
val surfaceContainerHighDark = Color(0xFF282B24)
val surfaceContainerHighestDark = Color(0xFF33362E)


/**
 * 生成随机颜色，方便查看重组情况
 */
fun randomComposeColor(): Color {
    return Color(
        red = Random.nextInt(256),
        green = Random.nextInt(256),
        blue = Random.nextInt(256),
        alpha = 255
    )
}


val editorBackground = Color(0xFFFFFFFF) // 白色
val editorBackgroundDark = Color(0xff1e1f22) // 灰色
val editorRootBackground = Color(0xFFA6A7AA)
val editorRootBackgroundDark = Color(0xff393b40)

val editorKeywordColor = Color(0xFF0000FF) // 蓝色
val editorStringColor = Color(0xFF800000) // 红色
val editorCommentColor = Color(0xFF3C783C) // 绿色
val editorNumberColor = Color(0xFF09885A) // 蓝绿色
val editorSelectedArea = Color(0xFFADD6FF) // 浅蓝色
val editorErrorMarker = Color(0xFFFF0000) // 红色
val editorWarningMarker = Color(0xFFFFC600) // 黄色
val editorWindowBorder = Color(0xFFE6E6E6) // 浅灰色


fun codeTextColor(isDark: Boolean): Color {
    return if (isDark) Color(0xFFBCBEC4) else Color(0xFF2B2D30)
}

fun editorRootBackground(isDark: Boolean): Color {
    return if (isDark) editorRootBackgroundDark else editorRootBackground
}

fun editorBackground(isDark: Boolean): Color {
    return if (isDark) editorBackgroundDark else editorBackground
}

fun codeKeywordColor(isDark: Boolean): Color {
    return if (isDark) Color(0xFFF5A623) else Color(0xFFF5A623)
}

fun editorBarBackground(isDark: Boolean): Color {
    return if (isDark) Color(0xff2b2d30) else Color(0xFFE6E6E6)
}





