package com.hold.ui.endgame.ask

import androidx.lifecycle.ViewModel
import com.hold.arch.common.livedata.SingleLiveEvent
import com.hold.domain.model.RecordType
import com.hold.domain.usecase.user.GetUserResultsUseCase
import com.hold.ui.leaderboard.model.LeaderboardActions
import com.hold.ui.leaderboard.model.LeaderboardRoute
import com.hold.ui.leaderboard.model.LeaderboardState
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AskContinueViewModel(
    private val getUserLocalResultsUseCase: GetUserResultsUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<LeaderboardState> =
        MutableStateFlow(LeaderboardState())
    val state: StateFlow<LeaderboardState> = _state

    private val _steps: SingleLiveEvent<LeaderboardRoute> = SingleLiveEvent()
    val steps: SingleLiveEvent<LeaderboardRoute> = _steps

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    init {

    }

    fun setInputAction(action: LeaderboardActions) {
        when (action) {
            LeaderboardActions.ClickOnBack -> goBack()
            is LeaderboardActions.ClickOnRecordChange -> clickOnChangeRecords(action.record)
        }

    }

    private fun goBack() {
        _steps.value = LeaderboardRoute.OnBack
    }

    private fun clickOnChangeRecords(record: RecordType) {
        _state.value = _state.value.copy(selectedRecords = record)
    }


    fun onMyRecordsClicked() {


    }

    fun onWorldRecordsClicked() {
//        state.records = user.records
    }
}

