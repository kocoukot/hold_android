package com.kocoukot.holdgame.ui.button.content

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kocoukot.holdgame.common.compose.theme.HTheme


@Composable
fun CustomComponent(
    duration: Long,
    canvasSize: Dp = 400.dp,
    indicatorValue: Int = 360,
    backgroundIndicatorColor: Color = HTheme.colors.primaryCircle30,
    backgroundIndicatorStrokeWidth: Float = 50f,
    foregroundIndicatorColor: Color = MaterialTheme.colors.primary,
    foregroundIndicatorStrokeWidth: Float = 50f,
) {
    var allowedIndicatorValue by remember { mutableStateOf(indicatorValue) }

    var animatedIndicatorValue by remember { mutableStateOf(0f) }

    LaunchedEffect(key1 = allowedIndicatorValue) {
        animatedIndicatorValue = allowedIndicatorValue.toFloat()
    }

    val sweepAngle by animateFloatAsState(
        targetValue = animatedIndicatorValue,
        animationSpec = tween((duration).toInt(), easing = LinearEasing)
    )

    Column(
        modifier = Modifier
            .size(canvasSize)
            .rotate(-90f)
            .drawBehind {
                val componentSize = size / 1.25f
//                backgroundIndicator(
//                    componentSize = componentSize,
//                    indicatorColor = backgroundIndicatorColor,
//                    indicatorStrokeWidth = backgroundIndicatorStrokeWidth,
////                    indicatorStokeCap = indicatorStrokeCap
//                )
                foregroundIndicator(
                    sweepAngle = sweepAngle,
                    componentSize = componentSize,
                    indicatorColor = foregroundIndicatorColor,
                    indicatorStrokeWidth = foregroundIndicatorStrokeWidth,
//                    indicatorStokeCap = indicatorStrokeCap
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }
}

@Preview
@Composable
fun PreviewB() {
    CustomComponent(
        duration = 30,
        indicatorValue = 360,
    )
}

fun DrawScope.foregroundIndicator(
    sweepAngle: Float,
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
//    indicatorStokeCap: StrokeCap
) {
    drawArc(
        size = componentSize,
        brush = Brush.sweepGradient(
            colors = listOf(Color(0xFFFFFFFF), Color(0xFF5A9DFF), Color(0xFF0068FF)),
        ),
        startAngle = 5f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

//fun DrawScope.backgroundIndicator(
//    componentSize: Size,
//    indicatorColor: Color,
//    indicatorStrokeWidth: Float,
////    indicatorStokeCap: StrokeCap
//) {
//    drawArc(
//        size = componentSize,
//        brush = Brush.radialGradient(colors = listOf(Color.White, Color.Red)),
//        startAngle = 0f,
//        sweepAngle = 360f,
//        useCenter = false,
//        style = Stroke(
//            width = indicatorStrokeWidth,
//            cap = StrokeCap.Round,
//        ),
//        topLeft = Offset(
//            x = (size.width - componentSize.width) / 2f,
//            y = (size.height - componentSize.height) / 2f
//        )
//    )
//}