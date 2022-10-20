package com.kocoukot.holdgame.ui.button.model

import com.android.billingclient.api.ProductDetails
import com.kocoukot.holdgame.core_mvi.ComposeState
import com.kocoukot.holdgame.model.user.GameUser
import com.kocoukot.holdgame.ui.button.domain.CouldContinueType
import com.kocoukot.holdgame.ui.button.domain.EndgameModel
import com.kocoukot.holdgame.ui.button.domain.EndgameState
import com.kocoukot.holdgame.ui.button.domain.GameState

data class MainGameState(
    val errorText: String = "",
    val isLoading: Boolean = false,
    val gameState: GameState = GameState.BUTTON,
    val couldContinue: CouldContinueType = CouldContinueType.NONE,
    val endgameState: EndgameState = EndgameState.END_OR_CONTINUE,
    val timer: Long? = null,
    val gameRecord: Long? = null,
    val endGameScreen: Boolean = false,
    val endGameData: EndgameModel? = null,
    val gameUser: GameUser? = null,
    val isAddLoaded: Boolean = false,
    val productDetails: List<ProductDetails> = emptyList()
) : ComposeState