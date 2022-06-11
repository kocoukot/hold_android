package com.hold.ui.leaderboard.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hold.common.compose.theme.HTheme
import com.hold.domain.model.user.GameRecord
import com.hold.domain.model.user.GameUser
import com.hold.utils.DateUtil.getRecordDate
import com.hold.utils.DateUtil.getRecordResult
import timber.log.Timber

@Composable
fun PersonalRecordRow(index: Int, record: GameRecord) {

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
fun GlobalRecordRow(index: Int, gameUser: GameUser) {

    val userRecord = gameUser.records.maxOf { it.result }
    val recordDate = gameUser.records.find { it.result == userRecord }!!
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
//            NetworkImage()
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(gameUser.userName, style = HTheme.typography.commonText)
                Text(
                    getRecordDate(recordDate.date),
                    style = HTheme.typography.commonText,
                    color = HTheme.colors.secondaryWhite50
                )
            }
        }
        Text(getRecordResult(recordDate.result), style = HTheme.typography.titleHeader)
    }
}