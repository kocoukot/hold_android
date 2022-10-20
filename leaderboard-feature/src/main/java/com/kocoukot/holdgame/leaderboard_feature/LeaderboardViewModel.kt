package com.kocoukot.holdgame.leaderboard_feature

import androidx.lifecycle.viewModelScope
import com.kocoukot.holdgame.core_mvi.BaseViewModel
import com.kocoukot.holdgame.core_mvi.ComposeActions
import com.kocoukot.holdgame.core_mvi.ReceiveEvent
import com.kocoukot.holdgame.leaderboard_feature.domain.usecase.GetGlobalResultsUseCase
import com.kocoukot.holdgame.leaderboard_feature.domain.usecase.GetUserMaxRecordUseCase
import com.kocoukot.holdgame.leaderboard_feature.domain.usecase.GetUserResultsUseCase
import com.kocoukot.holdgame.leaderboard_feature.model.LeaderboardActions
import com.kocoukot.holdgame.leaderboard_feature.model.LeaderboardModel
import com.kocoukot.holdgame.leaderboard_feature.model.LeaderboardRoute
import com.kocoukot.holdgame.leaderboard_feature.model.LeaderboardState
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
), ReceiveEvent {

    init {
        updateInfo { copy(isLoading = true) }

        viewModelScope.launch {
            supervisorScope {
                try {
                    val userResultsAsync = async { getUserLocalResultsUseCase() }
                    val globalUsersAsync = async { getGlobalResultsUseCase() }
                    val userMaxRecordAsync = async { getUserMaxRecordUseCase() }
                    updateInfo {
                        copy(
                            data = LeaderboardModel(
                                personalRecords = userResultsAsync.await(),
                                worldRecordRecords = globalUsersAsync.await(),
                                localUSerRecord = userMaxRecordAsync.await(),
                            )
                        ).also {
                            updateInfo { copy(isLoading = false) }
                        }
                    }

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

    override fun setInputActions(action: ComposeActions) {
        when (action) {
            LeaderboardActions.ClickOnBack -> sendEvent(LeaderboardRoute.OnBack)
            is LeaderboardActions.ClickOnRecordChange -> updateInfo { copy(selectedRecords = action.record) }
        }
    }
}

