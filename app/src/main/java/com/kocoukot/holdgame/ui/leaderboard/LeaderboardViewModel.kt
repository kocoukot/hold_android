package com.kocoukot.holdgame.ui.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kocoukot.holdgame.arch.common.livedata.SingleLiveEvent
import com.kocoukot.holdgame.domain.model.RecordType
import com.kocoukot.holdgame.domain.usecase.leaderboard.GetGlobalResultsUseCase
import com.kocoukot.holdgame.domain.usecase.leaderboard.GetUserResultsUseCase
import com.kocoukot.holdgame.ui.leaderboard.model.LeaderboardActions
import com.kocoukot.holdgame.ui.leaderboard.model.LeaderboardModel
import com.kocoukot.holdgame.ui.leaderboard.model.LeaderboardRoute
import com.kocoukot.holdgame.ui.leaderboard.model.LeaderboardState
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class LeaderboardViewModel(
    getUserLocalResultsUseCase: GetUserResultsUseCase,
    getGlobalResultsUseCase: GetGlobalResultsUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<LeaderboardState> =
        MutableStateFlow(LeaderboardState())
    val state = _state.asStateFlow()

    private val _steps: SingleLiveEvent<LeaderboardRoute> = SingleLiveEvent()
    val steps: SingleLiveEvent<LeaderboardRoute> = _steps

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    init {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            supervisorScope {
                try {
                    val localUser = async { getUserLocalResultsUseCase() }
                    val globalUsers = async { getGlobalResultsUseCase() }

                    _state.value = _state.value.copy(
                        data = LeaderboardModel(
                            personalRecords = localUser.await(),
                            worldRecordRecords = globalUsers.await()
                        )
                    )
                    _state.value = _state.value.copy(isLoading = false)
                } catch (e: Exception) {
                    _state.value = _state.value.copy(isLoading = false)
                    _state.value =
                        _state.value.copy(errorText = e.localizedMessage ?: "Oops! Some error :(")
                }
            }
        }

    }

    fun setInputActions(action: LeaderboardActions) {
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
}

