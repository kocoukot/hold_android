package com.kocoukot.holdgame.ui.leaderboard.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kocoukot.holdgame.R
import com.kocoukot.holdgame.common.compose.theme.HTheme
import com.kocoukot.holdgame.domain.model.user.GameGlobalUser
import com.kocoukot.holdgame.domain.model.user.GameResult
import com.kocoukot.holdgame.ui.button.content.CircularGameProgress
import com.kocoukot.holdgame.utils.DateUtil.getRecordDate
import com.kocoukot.holdgame.utils.DateUtil.getRecordResult
import timber.log.Timber


@Composable
fun PersonalRecordRow(
    index: Int, record: GameResult
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                index.toString(),
                style = HTheme.typography.titleHeader,
                modifier = Modifier
                    .padding(end = 16.dp)
            )
            Text(getRecordDate(record.date), style = HTheme.typography.commonText)
        }
        Timber.i("recordResult ${record.result}")
        Text(getRecordResult(record.result), style = HTheme.typography.titleHeader)
    }
}

@Composable
fun GlobalRecordRow(
    myRecord: GameResult?,
    index: Int,
    gameUser: GameGlobalUser,
    userId: String
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                index.toString(),
                style = HTheme.typography.titleHeader,
                modifier = Modifier
                    .width(30.dp)
            )

            gameUser.avatar?.let {
//            NetworkImage()
            } ?: run {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_unname_user),
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                            .background(HTheme.colors.primaryWhite30, CircleShape)
                            .padding(8.dp),
                        tint = HTheme.colors.primaryWhite
                    )

                    val progress = ((myRecord?.result ?: 0.0).toFloat() /
                            (gameUser.records.result.takeIf { it > 0 }?.toFloat()
                                ?: 0.001).toFloat())
                        .coerceAtLeast(0f)
                    val progressOffset = (1 - progress)

                    CircularGameProgress(
                        backgroundColor = Brush.sweepGradient(
                            colors = listOf(
                                Color(0xFFA2C7FF),
                                Color(0xFF183CF5),
                                Color(0xFF0013B6)
                            ),
                        ),
                        progress = progressOffset,
                        strokeWidth = 4.dp,
                        modifier = Modifier.size(48.dp),
                        foregroundColor = HTheme.colors.primaryWhite
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,

                ) {

                Text(
                    gameUser.userName,
                    style = HTheme.typography.commonText,
                    maxLines = 2,
                    color = if (gameUser.id.equals(userId, true))
                        HTheme.colors.primaryBlue
                    else
                        HTheme.colors.primaryWhite,
                    softWrap = true,
                    textAlign = TextAlign.Justify,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(120.dp)
                )

                Text(
                    getRecordDate(gameUser.records.date),
                    style = HTheme.typography.commonText,
                    color = HTheme.colors.primaryWhite50
                )
            }
            Text(
                getRecordResult(gameUser.records.result),
                style = HTheme.typography.titleHeader,
                fontSize = 18.sp,
                modifier = Modifier
                    .weight(1f),
                textAlign = TextAlign.End,
            )
        }

    }
}