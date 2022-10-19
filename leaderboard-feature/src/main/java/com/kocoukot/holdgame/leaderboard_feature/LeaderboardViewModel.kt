package com.kocoukot.holdgame.leaderboard_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kocoukot.holdgame.core.SingleLiveEvent
import com.kocoukot.holdgame.leaderboard_feature.domain.usecase.GetGlobalResultsUseCase
import com.kocoukot.holdgame.leaderboard_feature.domain.usecase.GetUserLocalRecordUseCase
import com.kocoukot.holdgame.leaderboard_feature.domain.usecase.GetUserResultsUseCase
import com.kocoukot.holdgame.leaderboard_feature.model.*
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class LeaderboardViewModel(
    getUserLocalResultsUseCase: GetUserResultsUseCase,
    getGlobalResultsUseCase: GetGlobalResultsUseCase,
    getUserLocalRecordUseCase: GetUserLocalRecordUseCase,
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
                    val localUserRecord = async { getUserLocalRecordUseCase() }

                    _state.value = _state.value.copy(
                        data = LeaderboardModel(
                            localUSerRecord = localUserRecord.await(),
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
        _state.update { it.copy(selectedRecords = record) }
    }
}

