package com.hold.ui.leaderboard.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hold.common.compose.theme.HTheme
import com.hold.domain.model.user.GameRecord
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