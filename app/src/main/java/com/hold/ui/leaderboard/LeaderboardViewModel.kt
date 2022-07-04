package com.hold.ui.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hold.arch.common.livedata.SingleLiveEvent
import com.hold.domain.model.RecordType
import com.hold.domain.usecase.leaderboard.GetGlobalResultsUseCase
import com.hold.domain.usecase.leaderboard.GetUserResultsUseCase
import com.hold.ui.leaderboard.model.LeaderboardActions
import com.hold.ui.leaderboard.model.LeaderboardModel
import com.hold.ui.leaderboard.model.LeaderboardRoute
import com.hold.ui.leaderboard.model.LeaderboardState
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
                } catch (e: Exception) {
                    _state.value =
                        _state.value.copy(errorText = e.localizedMessage ?: "Oops! Some error :(")
                }
            }.also {
                _state.value = _state.value.copy(isLoading = false)
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


    fun onMyRecordsClicked() {


    }

    fun onWorldRecordsClicked() {
//        state.records = user.records
    }
}

