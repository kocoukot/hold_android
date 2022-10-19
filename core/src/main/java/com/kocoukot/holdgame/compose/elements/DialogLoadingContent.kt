package com.kocoukot.holdgame.ui.common.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kocoukot.holdgame.compose.theme.HTheme

@Composable
fun DialogLoadingContent() {
    Dialog(
        onDismissRequest = { },
        DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                color = HTheme.colors.primaryBlue,
            )
        }
    }
}
