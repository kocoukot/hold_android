package com.kocoukot.holdgame.ui.button.model

import com.android.billingclient.api.ProductDetails
import com.kocoukot.holdgame.core_mvi.ComposeState
import com.kocoukot.holdgame.domain.model.CouldContinueType
import com.kocoukot.holdgame.domain.model.EndgameModel
import com.kocoukot.holdgame.domain.model.GameState
import com.kocoukot.holdgame.domain.model.user.GameUser
import com.kocoukot.holdgame.ui.button.domain.EndgameState

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