package com.hold.ui.endgame.ask

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hold.R
import com.hold.common.compose.theme.HTheme
import com.hold.domain.model.RecordType
import com.hold.ui.leaderboard.model.LeaderboardActions
import com.hold.ui.leaderboard.screen.PersonalRecordRow
import com.hold.ui.leaderboard.screen.RecordTabBar

@Composable
fun AskContinueScreeContent(viewModel: AskContinueViewModel) {

    var tabPage by remember { mutableStateOf(RecordType.PERSONAL) }
    val state = viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { viewModel.setInputAction(LeaderboardActions.ClickOnBack) },
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_navigation),
                        contentDescription = null,
                        tint = HTheme.colors.primaryWhite
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
        backgroundColor = HTheme.colors.primaryBackGround,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        )
        {
            RecordTabBar(
                backgroundColor = HTheme.colors.primaryBackGround,
                tabPage = tabPage,
                onTabSelected = {
                    tabPage = it
                    viewModel.setInputAction(LeaderboardActions.ClickOnRecordChange(it))
                }
            )


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 20.dp
                    )
            ) {

                when (state.value.selectedRecords) {
                    RecordType.PERSONAL -> {
                        state.value.data?.personalRecords?.let { personal ->

                            itemsIndexed(personal.records) { index, record ->
                                PersonalRecordRow(index + 1, record)
                            }


                        }
                    }
                    RecordType.GLOBAL -> {

                    }
                }
            }
        }
    }
}



