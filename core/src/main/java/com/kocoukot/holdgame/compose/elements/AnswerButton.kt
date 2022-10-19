package com.kocoukot.holdgame.compose.elements

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kocoukot.holdgame.compose.theme.HTheme
import com.kocoukot.holdgame.core.R
import com.kocoukot.holdgame.model.EndgameButtons

@Composable
fun AnswerButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    buttonType: EndgameButtons,
    onButtonClick: (EndgameButtons) -> Unit
) {

    val buttonColor =
        if (buttonType == EndgameButtons.CONTINUE ||
            buttonType == EndgameButtons.PAY ||
            buttonType == EndgameButtons.PAY_ONCE ||
            buttonType == EndgameButtons.SAVE_NICKNAME
        )
            HTheme.colors.primaryBlue
        else
            Color.Transparent
    Button(
        enabled = buttonType != EndgameButtons.SAVE_NICKNAME_EMPTY,
        contentPadding = PaddingValues(vertical = 14.dp),
        modifier = modifier,
        elevation = ButtonDefaults.elevation(0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, HTheme.colors.primaryBlue),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor,
            disabledBackgroundColor = Color.Transparent
        ),
        onClick = { onButtonClick(buttonType) }) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (buttonType == EndgameButtons.WATCH) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_watch),
                    contentDescription = null,
                    tint = HTheme.colors.primaryWhite
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
            Text(buttonText, style = HTheme.typography.commonText)
        }
    }

}


@Preview
@Composable
fun AnswerButtonPreview() {
    AnswerButton(
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                )
            ),
        "positiveButtonText",
        EndgameButtons.CONTINUE
    ) {
    }
}