package com.kocoukot.holdgame.ui.button.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kocoukot.holdgame.R
import com.kocoukot.holdgame.common.compose.theme.HTheme
import com.kocoukot.holdgame.ui.button.coloredShadow
import com.kocoukot.holdgame.ui.button.model.ButtonActions
import com.kocoukot.holdgame.utils.DateUtil
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@Composable
fun MainGameContent(
    modifier: Modifier,
    timer: Long?,
    record: Long,
    buttonAction: (ButtonActions) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val interactions = remember { mutableStateListOf<Interaction>() }
    var hintVisibility by remember { mutableStateOf(true) }

    val isPressed = remember { mutableStateOf(false) }
    val buttonColor =
        animateColorAsState(
            targetValue = if (isPressed.value) HTheme.colors.primaryWhite50
            else HTheme.colors.primaryWhite
        )

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            println("interaction $interaction")
            isPressed.value = !isPressed.value
            when (interaction) {
                is PressInteraction.Press -> {
                    hintVisibility = false
                    buttonAction.invoke(ButtonActions.PressDownButton)
                    interactions.add(interaction)
                }
                is PressInteraction.Release -> {
                    buttonAction.invoke(ButtonActions.PressUpButton)
                    interactions.remove(interaction.press)
                }
                is PressInteraction.Cancel -> {
                    buttonAction.invoke(ButtonActions.PressUpButton)
                    interactions.remove(interaction.press)
                }
            }
        }
    }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 120.dp)
    ) {
        val (button, text, hint) = createRefs()


        val time = timer?.let { time ->
            DateUtil.timeTimerFormat(time)
        } ?: "00:00:00"

        Text(
            time.toString(),
            style = HTheme.typography.titleHeader,
            fontSize = 54.sp,
            modifier = Modifier.constrainAs(text) {
                bottom.linkTo(button.top, margin = 140.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        val infiniteTransition = rememberInfiniteTransition()
        val labelMargin = infiniteTransition.animateValue(
            initialValue = 0.dp,
            targetValue = 15.dp,
            typeConverter = Dp.VectorConverter,
            animationSpec = infiniteRepeatable(
                animation = tween(600, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        Column(
            modifier = Modifier.constrainAs(hint) {
                bottom.linkTo(button.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            AnimatedVisibility(
                visible = hintVisibility,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.padding(bottom = labelMargin.value)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_hold_label),
                    contentDescription = null,
                )
            }
        }


        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(230.dp)
                .constrainAs(button) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        ) {

            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = HTheme.colors.primaryBackground),
                onClick = { },
                interactionSource = interactionSource,
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .size(200.dp)
                    .coloredShadow()
                    .shadow(24.dp, CircleShape, true),
                border = BorderStroke(width = 15.dp, Color(0x4DFFFFFF))
            ) {}

            Text(
                text = "H",
                fontSize = 72.sp,
                color = buttonColor.value,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W700
            )

            if (timer != null && timer > 0) {
                Timber.d("record $record")
                CustomComponent(
                    duration = record,
                    canvasSize = 230.dp,
                    foregroundIndicatorStrokeWidth = 45f
                )
            }
        }
    }
}