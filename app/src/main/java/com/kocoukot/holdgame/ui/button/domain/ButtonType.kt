package com.kocoukot.holdgame.ui.button.domain

import com.kocoukot.holdgame.domain.model.EndgameButtons
import com.kocoukot.holdgame.ui.button.model.ButtonActions


data class ButtonType(
    val buttonType: EndgameButtons,
    val buttonTitle: Int,
    val buttonAction: ButtonActions,
)