package com.kocoukot.holdgame.ui.button.model

import com.kocoukot.holdgame.model.EndgameButtons


data class ButtonType(
    val buttonType: EndgameButtons,
    val buttonTitle: Int,
    val buttonAction: ButtonActions,
)