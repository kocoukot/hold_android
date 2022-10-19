package com.kocoukot.holdgame.compose.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kocoukot.holdgame.compose.theme.HTheme
import com.kocoukot.holdgame.core.R
import com.kocoukot.holdgame.model.EndgameButtons


@Composable
fun NameInputContent(
    userName: String = "",
    saveName: (String) -> Unit
) {
    var inputText by remember { mutableStateOf(userName) }
   // if (userName.isNotEmpty()) inputText = userName

    var buttonType by remember { mutableStateOf(EndgameButtons.SAVE_NICKNAME_EMPTY) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {


        Image(
            painter = painterResource(id = R.drawable.img_blur),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(bottom = 30.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.say_your_name),
                    style = HTheme.typography.subtitleHeader,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 60.dp)
                )

                TextField(
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                    keyboardActions = KeyboardActions(
                        onAny = {
                            saveName.invoke(inputText.trim())
                        }
                    ),
                    value = inputText,
                    onValueChange = {
                        if (it.length < 20) {
                            inputText = it
                            buttonType =
                                if (it.isEmpty()) EndgameButtons.SAVE_NICKNAME_EMPTY else EndgameButtons.SAVE_NICKNAME
                        }
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.nickname_placeholder),
                            style = HTheme.typography.commonText,
                            textAlign = TextAlign.Start,
                            color = HTheme.colors.primaryWhite50
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .padding(horizontal = 90.dp)
                        .padding(bottom = 40.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.White,
                        textColor = Color.White,
                        focusedIndicatorColor = Color.White.copy(alpha = 0.9f),
                        unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                    )

                )
            }



            AnswerButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 60.dp),
                buttonText = stringResource(id = R.string.save_text),
                buttonType = buttonType,
                onButtonClick = {
                    saveName.invoke(inputText.trim())
                },
            )
        }
    }
}


@Preview
@Composable
fun NameInputContentPreview() {
    NameInputContent(
        saveName = {

        })
}