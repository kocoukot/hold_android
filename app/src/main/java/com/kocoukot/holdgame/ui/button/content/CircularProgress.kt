package com.kocoukot.holdgame.ui.button.content

import androidx.compose.foundation.Canvas
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun CircularGameProgress(
    progress: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Brush = Brush.sweepGradient(
        colors = listOf(Color(0xFFFFFFFF), Color(0xFF183CF5), Color(0xFF0013B6)),
    ),
    foregroundColor: Color? = null,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth,
) {
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
    }
    Canvas(
        modifier
            .rotate(-90f)


    ) {
        val startAngle = 6f
        val sweep = progress * 360f
        val diameterOffset = stroke.width / 2
        foregroundColor?.let {
            drawArcIndicator(startAngle, sweep, foregroundColor, stroke, diameterOffset)

        }
        drawArcBackground(
            startAngle,
            (1 - progress) * 360,
            backgroundColor,
            stroke,
            diameterOffset
        )

    }
}

fun DrawScope.drawArcBackground(
    startAngle: Float,
    sweep: Float,
    brush: Brush,
    stroke: Stroke,
    diameterOffset: Float
) {
    val arcDimen = size.width - 2 * diameterOffset
    drawArc(
        brush = brush,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}


fun DrawScope.drawArcIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke,
    diameterOffset: Float
) {
    val arcDimen = size.width - 2 * diameterOffset
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = -sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}
