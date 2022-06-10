import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hold.R
import com.hold.common.compose.theme.HTheme
import com.hold.domain.model.RecordType
import com.hold.ui.leaderboard.LeaderboardViewModel
import com.hold.ui.leaderboard.model.LeaderboardActions
import com.hold.ui.leaderboard.screen.PersonalRecordRow
import com.hold.ui.leaderboard.screen.RecordTabBar

@Composable
fun LeaderboardScreeContent(viewModel: LeaderboardViewModel) {

    var tabPage by remember { mutableStateOf(RecordType.PERSONAL) }
    val state = viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth(),
                title = {
                    Text(
                        text = stringResource(id = R.string.achievements),
                        style = HTheme.typography.titleHeader,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
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
                },
                backgroundColor = HTheme.colors.primaryBackGround,
            )
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



