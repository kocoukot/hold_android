package com.kocoukot.holdgame.leaderboard_feature

import androidx.lifecycle.viewModelScope
import com.kocoukot.holdgame.core_mvi.BaseViewModel
import com.kocoukot.holdgame.leaderboard_feature.domain.usecase.GetGlobalResultsUseCase
import com.kocoukot.holdgame.leaderboard_feature.domain.usecase.GetUserMaxRecordUseCase
import com.kocoukot.holdgame.leaderboard_feature.domain.usecase.GetUserResultsUseCase
import com.kocoukot.holdgame.leaderboard_feature.model.*
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class LeaderboardViewModel(
    getUserLocalResultsUseCase: GetUserResultsUseCase,
    getGlobalResultsUseCase: GetGlobalResultsUseCase,
    getUserMaxRecordUseCase: GetUserMaxRecordUseCase,
) : BaseViewModel.Base<LeaderboardState>(
    mState = MutableStateFlow(LeaderboardState())
) {

    init {
        updateInfo { copy(isLoading = true) }

        viewModelScope.launch {
            supervisorScope {
                try {
                    val userResultsAsync = async { getUserLocalResultsUseCase() }
                    val globalUsersAsync = async { getGlobalResultsUseCase() }
                    val userMaxRecordAsync = async { getUserMaxRecordUseCase() }
                    val userRecords = userResultsAsync.await()
                    val globalRecords = globalUsersAsync.await()
                    val userMaxRecord = userMaxRecordAsync.await()
                    updateInfo {
                        copy(
                            data = LeaderboardModel(
                                personalRecords = userRecords,
                                worldRecordRecords = globalRecords,
                                localUSerRecord = userMaxRecord,
                            )
                        )
                    }
                    updateInfo { copy(isLoading = false) }
                } catch (e: Exception) {
                    updateInfo {
                        copy(
                            isLoading = false,
                            errorText = e.localizedMessage ?: "Oops! Some error :("
                        )
                    }
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
        sendEvent(LeaderboardRoute.OnBack)
    }

    private fun clickOnChangeRecords(record: RecordType) {
        updateInfo { copy(selectedRecords = record) }
    }
}

