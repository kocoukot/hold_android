package com.kocoukot.holdgame.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kocoukot.holdgame.R
import com.kocoukot.holdgame.common.compose.theme.HTheme
import com.kocoukot.holdgame.ui.button.content.NameInputContent
import com.kocoukot.holdgame.ui.profile.model.ProfileActions

@Composable
fun ProfileScreeContent(viewModel: ProfileViewModel) {

    val state = viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.setInputActions(ProfileActions.ClickOnBack) },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close),
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
                .fillMaxSize()
        ) {
            NameInputContent(
                userName = state.value.userNickname,
                saveName = { viewModel.setInputActions(ProfileActions.ClickOnSaveNickname(it)) }
            )
        }
    }
}
