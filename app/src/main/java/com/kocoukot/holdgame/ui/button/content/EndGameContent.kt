package com.kocoukot.holdgame.ui.button.content

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kocoukot.holdgame.R
import com.kocoukot.holdgame.common.compose.theme.HTheme
import com.kocoukot.holdgame.domain.model.EndgameButtons
import com.kocoukot.holdgame.domain.model.EndgameModel
import com.kocoukot.holdgame.domain.model.EndgameState
import com.kocoukot.holdgame.ui.button.model.ButtonActions
import com.kocoukot.holdgame.ui.common.compose.AnswerButton
import com.kocoukot.holdgame.utils.DateUtil


@Composable
fun EndGameContent(
    endGameState: EndgameState,
    endGameModel: EndgameModel,
    onActionClicked: (ButtonActions) -> Unit
) {
    val record = endGameModel.recordValue?.result ?: 0
    val lastResult = endGameModel.currentValue?.result ?: 0
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {


        Image(
            painter = painterResource(id = R.drawable.img_blur),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                modifier = Modifier.size(250.dp, 250.dp),
                painter = painterResource(id = R.drawable.img_hand),
                contentDescription = null
            )

            val titleText = if (endGameState == EndgameState.PAY_AMOUNT) {
                stringResource(id = R.string.endgame_question_pay)
            } else {
                stringResource(id = R.string.endgame_question)
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                titleText,
                style = HTheme.typography.subtitleHeader,
                textAlign = TextAlign.Center,

                )
            Spacer(modifier = Modifier.height(10.dp))


            if (record > lastResult) {
                val notEnoughForRecord = record - lastResult


                Text(
                    stringResource(
                        id = R.string.not_enough_for_record,
                        DateUtil.toRecordResolve(notEnoughForRecord)
                    ),
                    style = HTheme.typography.textGray,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(14.dp))

            }


            Column(
                modifier = Modifier.padding(horizontal = 8.dp),

                ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val positiveButtonType = when (endGameState) {
                        EndgameState.END_OR_CONTINUE -> EndgameButtons.CONTINUE
                        EndgameState.PAY_AMOUNT -> EndgameButtons.PAY
                        EndgameState.PAY_OR_WATCH -> EndgameButtons.PAY_ONCE
                    }
                    val positiveButtonText = when (endGameState) {
                        EndgameState.END_OR_CONTINUE -> stringResource(id = R.string.continue_game)
                        EndgameState.PAY_OR_WATCH -> stringResource(id = R.string.pay_to_continue)
                        EndgameState.PAY_AMOUNT -> stringResource(id = R.string.pay_once)
                    }

                    val positiveButtonAction = when (endGameState) {
                        EndgameState.END_OR_CONTINUE -> ButtonActions.ClickOnContinue
                        EndgameState.PAY_OR_WATCH -> ButtonActions.ClickOnPay
                        EndgameState.PAY_AMOUNT -> ButtonActions.ClickOnPayOnce
                    }



                    AnswerButton(
                        modifier = Modifier
                            .weight(1f)
                            .animateContentSize(
                                animationSpec = tween(
                                    durationMillis = 300,
                                )
                            ),
                        positiveButtonText,
                        positiveButtonType
                    ) {
                        onActionClicked(positiveButtonAction)
                    }


                    AnimatedVisibility(
                        visible = endGameState == EndgameState.END_OR_CONTINUE,
                        modifier = Modifier
                            .weight(1f),
                        exit = slideOutHorizontally(
                            animationSpec = tween(300),
                            targetOffsetX = { 500 })
                    ) {
                        AnswerButton(
                            modifier = Modifier.weight(1f),
                            stringResource(id = R.string.cancel), EndgameButtons.CANCEL
                        ) {
                            onActionClicked(ButtonActions.ClickOnCancel)
                        }


                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                AnimatedVisibility(
                    visible = (endGameState == EndgameState.PAY_OR_WATCH || endGameState == EndgameState.PAY_AMOUNT),
                    enter = slideInVertically(animationSpec = tween(300)),
                    exit = slideOutVertically(animationSpec = tween(300))
                ) {
                    val negativeButtonType = when (endGameState) {
                        EndgameState.END_OR_CONTINUE -> EndgameButtons.CONTINUE
                        EndgameState.PAY_OR_WATCH -> EndgameButtons.WATCH
                        EndgameState.PAY_AMOUNT -> EndgameButtons.PAY_FOR_DAY
                    }

                    val negativeButtonText = when (endGameState) {
                        EndgameState.END_OR_CONTINUE -> stringResource(id = R.string.cancel)
                        EndgameState.PAY_OR_WATCH -> stringResource(id = R.string.watch_to_continue)
                        EndgameState.PAY_AMOUNT -> stringResource(id = R.string.pay_for_day)
                    }

                    val negativeButtonAction = when (endGameState) {
                        EndgameState.END_OR_CONTINUE -> ButtonActions.ClickOnContinue
                        EndgameState.PAY_OR_WATCH -> ButtonActions.ClickOnWatchAdd
                        EndgameState.PAY_AMOUNT -> ButtonActions.ClickOnPayDay
                    }
                    AnswerButton(
                        modifier = Modifier.fillMaxWidth(),
                        negativeButtonText,
                        negativeButtonType
                    ) {
                        onActionClicked(negativeButtonAction)
                    }
                }

            }
        }
    }
}