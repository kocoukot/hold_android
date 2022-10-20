package com.kocoukot.holdgame.compose.theme

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

val transformationSpec = SpringSpec<IntSize>(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = 200f
)

val movementSpec = SpringSpec<IntOffset>(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = 200f
)
