package com.hold.ui.button.model

import com.hold.domain.model.EndgameModel
import com.hold.domain.model.EndgameState

data class MainGameState(
    val errorText: String = "",
    val isLoading: Boolean = false,
    val isEndGame: Boolean = false,
//    val selectedRecords: RecordType = RecordType.PERSONAL,
    val endgameState: EndgameState = EndgameState.END_OR_CONTINUE,
    val timer: Long? = null,
    val gameRecord: Long? = null,
    val endGameScreen: Boolean = false,
    val endGameData: EndgameModel? = null,

    )