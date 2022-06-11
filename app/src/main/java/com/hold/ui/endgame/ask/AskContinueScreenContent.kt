package com.hold.ui.endgame.ask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties

@Composable
fun AskContinueScreeContent(viewModel: AskContinueViewModel) {

    val state = viewModel.state.collectAsState()

    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            backgroundColor = Color.Transparent,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            onDismissRequest = {
                openDialog.value = false
            },

            confirmButton = {

            },
            dismissButton = {

            }
        )
    }
}






