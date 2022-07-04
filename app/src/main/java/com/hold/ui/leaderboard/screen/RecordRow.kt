package com.hold.ui.leaderboard.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hold.R
import com.hold.common.compose.theme.HTheme
import com.hold.domain.model.user.GameGlobalUser
import com.hold.domain.model.user.GameResult
import com.hold.utils.DateUtil.getRecordDate
import com.hold.utils.DateUtil.getRecordResult
import timber.log.Timber


@Composable
fun PersonalRecordRow(index: Int, record: GameResult) {

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
fun GlobalRecordRow(index: Int, gameUser: GameGlobalUser, userId: String) {


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
                    .padding(end = 16.dp)
            )

            gameUser.avatar?.let {
//            NetworkImage()
            } ?: run {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(HTheme.colors.primaryWhite30, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_unname_user),
                        contentDescription = null,
                        modifier = Modifier,
                        tint = HTheme.colors.primaryWhite
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .width(125.dp)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,

                ) {

                Text(
                    gameUser.userName,
                    style = HTheme.typography.commonText,
                    maxLines = 2,
                    color = if (gameUser.id.equals(
                            userId,
                            true
                        )
                    ) HTheme.colors.primaryBlue else HTheme.colors.primaryWhite,
                    softWrap = true,
                    textAlign = TextAlign.Justify,
//                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    getRecordDate(gameUser.records.date),
                    style = HTheme.typography.commonText,
                    color = HTheme.colors.primaryWhite50
                )
            }
        }
        Text(
            getRecordResult(gameUser.records.result),
            style = HTheme.typography.titleHeader,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
        )
    }
}