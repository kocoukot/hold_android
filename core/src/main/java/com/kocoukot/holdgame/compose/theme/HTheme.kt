package com.kocoukot.holdgame.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

object HTheme {

    val colors: HColors
        @Composable get() = LocalDSColor.current

    val typography: HTypography
        @Composable get() = LocalDSTypography.current
}

internal val LocalDSColor = compositionLocalOf { HColors() }
internal val LocalDSTypography = compositionLocalOf { HTypography() }