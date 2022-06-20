package com.hold.ui.button

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hold.R
import com.hold.common.compose.theme.HTheme
import com.hold.ui.button.content.EndGameContent
import com.hold.ui.button.content.MainGameContent
import com.hold.ui.button.model.ButtonActions

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainButtonScreenContent(viewModel: ButtonViewModel) {


    val state = viewModel.state.collectAsState()


    var isEndGame by remember { mutableStateOf(false) }
    isEndGame = state.value.isEndGame


    Box {
        Scaffold(
            backgroundColor = HTheme.colors.primaryBackground,
            topBar = {
                TopAppBar(
                    elevation = 0.dp,
                    backgroundColor = Color.Transparent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    title = {},
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                if (isEndGame)
                                    viewModel.setInputActions(ButtonActions.ClickOnBack)
                                else
                                    viewModel.setInputActions(ButtonActions.ClickOnToProfile)
                            },
                        ) {

                            Icon(
                                painter = painterResource(
                                    id =
                                    if (isEndGame)
                                        R.drawable.ic_close
                                    else
                                        R.drawable.ic_navigation
                                ),
                                contentDescription = null,
                                tint = HTheme.colors.primaryWhite
                            )
                        }
                    },
                    actions = {
                        AnimatedVisibility(visible = !isEndGame) {

                            IconButton(
                                onClick = {
                                    viewModel.setInputActions(ButtonActions.ClickOnToLeaderboard)
                                },
                                modifier = Modifier

                                    .size(46.dp)
                                    .background(
                                        HTheme.colors.primaryWhite30,
                                        RoundedCornerShape(12.dp)
                                    )
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_leaderboard),
                                    contentDescription = null,
                                    tint = HTheme.colors.primaryWhite
                                )
                            }
                        }
                    })

            }
        ) {


            AnimatedContent(
                modifier = Modifier.fillMaxSize(),
                targetState = isEndGame,
                contentAlignment = Alignment.Center,
            ) { targetState ->
                when (targetState) {
                    false -> {
                        MainGameContent(
                            modifier = Modifier.fillMaxSize(),
                            timer = state.value.timer,
                            record = state.value.gameRecord ?: 0,
                            buttonAction = {
                                viewModel.setInputActions(it)
                            })
                    }
                    true -> {
                        state.value.endGameData?.let { endGameData ->
                            EndGameContent(
                                endGameState = state.value.endgameState,
                                endGameModel = endGameData,
                                onActionClicked = {
                                    viewModel.setInputActions(it)
                                }
                            )
                        }
                    }
                }

            }

        }
    }
}


@Preview
@Composable
fun Modifier.coloredShadow(
    color: Color = Color.White,
    alpha: Float = 0.5f,
    borderRadius: Dp = 200.dp,
    shadowRadius: Dp = 200.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = composed {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparent = color.copy(alpha = 0f).toArgb()

    this.drawBehind {

        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparent

            frameworkPaint.setShadowLayer(
                shadowRadius.toPx(),
                offsetX.toPx(),
                offsetY.toPx(),
                shadowColor
            )
            it.drawRoundRect(
                0f,
                0f,
                this.size.width,
                this.size.height,
                borderRadius.toPx(),
                borderRadius.toPx(),
                paint
            )
        }
    }
}
