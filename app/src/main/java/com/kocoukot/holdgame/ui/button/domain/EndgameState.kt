package com.kocoukot.holdgame.ui.button.domain

import com.kocoukot.holdgame.R
import com.kocoukot.holdgame.model.EndgameButtons
import com.kocoukot.holdgame.ui.button.model.ButtonActions

enum class EndgameState(val buttonsList: List<ButtonType>) {
    END_OR_CONTINUE(
        listOf(
            ButtonType(
                buttonType = EndgameButtons.CONTINUE,
                buttonTitle = R.string.continue_game,
                buttonAction = ButtonActions.ClickOnContinue,
            ),
            ButtonType(
                buttonType = EndgameButtons.CANCEL,
                buttonTitle = R.string.cancel,
                buttonAction = ButtonActions.ClickOnCancel,
            ),
        )
    ),
    PAY_OR_WATCH(
        listOf(
            ButtonType(
                buttonType = EndgameButtons.PAY,
                buttonTitle = R.string.pay_to_continue,
                buttonAction = ButtonActions.ClickOnPay,
            ),
            ButtonType(
                buttonType = EndgameButtons.WATCH,
                buttonTitle = R.string.watch_to_continue,
                buttonAction = ButtonActions.ClickOnWatchAdd,
            ),
        )
    ),
    PAY_AMOUNT(
        listOf(
            ButtonType(
                buttonType = EndgameButtons.PAY_ONCE,
                buttonTitle = R.string.pay_once,
                buttonAction = ButtonActions.ClickOnPayOnce,
            ),

            ButtonType(
                buttonType = EndgameButtons.PAY_FOR_DAY,
                buttonTitle = R.string.pay_for_day,
                buttonAction = ButtonActions.ClickOnPayDay,
            ),
        )
    ),
}




