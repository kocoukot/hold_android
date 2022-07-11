package com.kocoukot.holdgame.ui.button.model

import com.android.billingclient.api.ProductDetails
import com.kocoukot.holdgame.domain.model.EndgameModel
import com.kocoukot.holdgame.domain.model.EndgameState
import com.kocoukot.holdgame.domain.model.user.GameUser

data class MainGameState(
    val errorText: String = "",
    val isLoading: Boolean = false,
    val gameState: GameState = GameState.BUTTON,
    val couldContinue: Boolean = false,
    val endgameState: EndgameState = EndgameState.END_OR_CONTINUE,
    val timer: Long? = null,
    val gameRecord: Long? = null,
    val endGameScreen: Boolean = false,
    val endGameData: EndgameModel? = null,
    val gameUser: GameUser? = null,
    val isAddLoaded: Boolean = false,
    val productDetails: List<ProductDetails> = emptyList()
)