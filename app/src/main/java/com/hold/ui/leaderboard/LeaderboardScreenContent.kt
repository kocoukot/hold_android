import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hold.R
import com.hold.common.compose.theme.HTheme
import com.hold.domain.model.RecordType
import com.hold.ui.common.compose.DialogLoadingContent
import com.hold.ui.leaderboard.LeaderboardViewModel
import com.hold.ui.leaderboard.model.LeaderboardActions
import com.hold.ui.leaderboard.screen.GlobalRecordRow
import com.hold.ui.leaderboard.screen.PersonalRecordRow
import com.hold.ui.leaderboard.screen.RecordTabBar

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LeaderboardScreeContent(viewModel: LeaderboardViewModel) {

    var tabPage by remember { mutableStateOf(RecordType.PERSONAL) }
    val state = viewModel.state.collectAsState()


    if (state.value.isLoading) {
        DialogLoadingContent()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.achievements),
                        style = HTheme.typography.titleHeader,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.setInputActions(LeaderboardActions.ClickOnBack) },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_navigation),
                            contentDescription = null,
                            tint = HTheme.colors.primaryWhite
                        )
                    }
                },
                backgroundColor = HTheme.colors.primaryBackground,
            )
        },
        modifier = Modifier.fillMaxSize(),
        backgroundColor = HTheme.colors.primaryBackground,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        )
        {
            RecordTabBar(
                backgroundColor = HTheme.colors.primaryBackground,
                tabPage = tabPage,
                onTabSelected = {
                    tabPage = it
                    viewModel.setInputActions(LeaderboardActions.ClickOnRecordChange(it))
                }
            )


            AnimatedContent(targetState = state.value.selectedRecords) { listState ->
                when (listState) {
                    RecordType.PERSONAL -> {
                        state.value.data?.personalRecords?.records?.let { records ->
                            if (records.isEmpty()) {
                                EmptyListLabel()
                            } else {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 20.dp
                                        )
                                ) {
                                    itemsIndexed(records) { index, record ->
                                        PersonalRecordRow(index + 1, record)
                                    }
                                }
                            }
                        }
                    }
                    RecordType.GLOBAL -> {
                        state.value.data?.worldRecordRecords?.let { records ->
                            if (records.isEmpty()) {
                                EmptyListLabel()
                            } else {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 20.dp
                                        )
                                ) {
                                    itemsIndexed(records) { index, gameUser ->
                                        GlobalRecordRow(
                                            index + 1,
                                            gameUser,
                                            state.value.data?.personalRecords?.id ?: ""
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}


@Composable
fun EmptyListLabel() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.empty_result_list),
            textAlign = TextAlign.Center,
            style = HTheme.typography.titleHeader
        )
    }
}


