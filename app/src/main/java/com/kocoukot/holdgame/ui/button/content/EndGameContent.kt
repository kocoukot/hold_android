package com.kocoukot.holdgame.ui.button.content

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.billingclient.api.ProductDetails
import com.kocoukot.holdgame.R
import com.kocoukot.holdgame.common.compose.movementSpec
import com.kocoukot.holdgame.common.compose.theme.HTheme
import com.kocoukot.holdgame.common.compose.transformationSpec
import com.kocoukot.holdgame.domain.model.EndgameButtons
import com.kocoukot.holdgame.domain.model.EndgameModel
import com.kocoukot.holdgame.domain.model.EndgameState
import com.kocoukot.holdgame.ui.button.model.ButtonActions
import com.kocoukot.holdgame.ui.common.Constant.ONE_DAY_PRODUCT_ID
import com.kocoukot.holdgame.ui.common.Constant.ONE_TRY_PRODUCT_ID
import com.kocoukot.holdgame.ui.common.compose.AnswerButton
import com.kocoukot.holdgame.utils.DateUtil
import com.skydoves.orbitary.Orbitary
import com.skydoves.orbitary.animateSharedElementTransition
import com.skydoves.orbitary.rememberContentWithOrbitaryScope


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EndGameContent(
    endGameState: EndgameState,
    endGameModel: EndgameModel,
    isAddLoaded: Boolean,
    productList: List<ProductDetails>,
    onActionClicked: (ButtonActions) -> Unit
) {
    val record = endGameModel.recordValue?.result ?: 0
    val lastResult = endGameModel.currentValue?.result ?: 0

    var isTransformed by remember { mutableStateOf(endGameState != EndgameState.END_OR_CONTINUE) }
    isTransformed = endGameState != EndgameState.END_OR_CONTINUE
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
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
                modifier = Modifier.padding(horizontal = 16.dp)
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

            val items = rememberContentWithOrbitaryScope {
                endGameState.buttonsList.forEach { item ->
                    val positiveButtonText = when (endGameState) {
                        EndgameState.PAY_AMOUNT -> {
                            productList.find { it.productId == if (item.buttonType == EndgameButtons.PAY_ONCE) ONE_TRY_PRODUCT_ID else ONE_DAY_PRODUCT_ID }?.oneTimePurchaseOfferDetails?.formattedPrice?.let { price ->
                                stringResource(id = item.buttonTitle, price)
                            } ?: ""
                        }
                        else -> {
                            stringResource(item.buttonTitle)
                        }
                    }

                    if (item.buttonType == EndgameButtons.WATCH && isAddLoaded) {
                        AnswerButton(
                            modifier = if (isTransformed) {
                                Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                                    .padding(horizontal = 8.dp)
                            } else {
                                Modifier
                                    .weight(1f)
                                    .padding(horizontal = 4.dp)
                            }
                                .animateSharedElementTransition(
                                    this,
                                    movementSpec,
                                    transformationSpec
                                ),
                            positiveButtonText,
                            item.buttonType
                        ) {
                            onActionClicked(item.buttonAction)
                        }
                    } else {


                        AnswerButton(
                            modifier = if (isTransformed) {
                                Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                                    .padding(horizontal = 8.dp)
                            } else {
                                Modifier
                                    .weight(1f)
                                    .padding(horizontal = 4.dp)
                            }
                                .animateSharedElementTransition(
                                    this,
                                    movementSpec,
                                    transformationSpec
                                ),
                            positiveButtonText,
                            item.buttonType
                        ) {
                            onActionClicked(item.buttonAction)
                        }
                    }
                }
            }
            Orbitary(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp),
                isTransformed = isTransformed,
                onStartContent = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        items()
                    }
                },
                onTransformedContent = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) { items() }
                }
            )
        }
    }
}