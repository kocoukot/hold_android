package com.kocoukot.holdgame.domain.model

import androidx.annotation.StringRes
import com.kocoukot.holdgame.R
import com.kocoukot.holdgame.ui.button.model.ButtonActions

enum class EndgameState(
    val posButtonType: EndgameButtons,
    @StringRes val posButtonTitle: Int,
    val posButtonAction: ButtonActions,
    val negButtonType: EndgameButtons,
    @StringRes val negButtonTitle: Int,
    val negButtonAction: ButtonActions,

    ) {
    END_OR_CONTINUE(
        posButtonType = EndgameButtons.CONTINUE,
        posButtonTitle = R.string.continue_game,
        posButtonAction = ButtonActions.ClickOnContinue,

        negButtonType = EndgameButtons.CONTINUE,
        negButtonTitle = R.string.cancel,
        negButtonAction = ButtonActions.ClickOnContinue,
    ),
    PAY_OR_WATCH(
        posButtonType = EndgameButtons.PAY,
        posButtonTitle = R.string.pay_to_continue,
        posButtonAction = ButtonActions.ClickOnPay,

        negButtonType = EndgameButtons.WATCH,
        negButtonTitle = R.string.watch_to_continue,
        negButtonAction = ButtonActions.ClickOnWatchAdd,
    ),
    PAY_AMOUNT(
        posButtonType = EndgameButtons.PAY_ONCE,
        posButtonTitle = R.string.pay_once,
        posButtonAction = ButtonActions.ClickOnPayOnce,

        negButtonType = EndgameButtons.PAY_FOR_DAY,
        negButtonTitle = R.string.pay_for_day,
        negButtonAction = ButtonActions.ClickOnPayDay,
    ),
}




