package com.kocoukot.holdgame.profile_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kocoukot.holdgame.domain.usecase.user.GetUserNameUseCase
import com.kocoukot.holdgame.domain.usecase.user.SaveUserNameUseCase
import com.kocoukot.holdgame.profile_feature.model.ProfileActions
import com.kocoukot.holdgame.profile_feature.model.ProfileRoute
import com.kocoukot.holdgame.profile_feature.model.ProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val saveUserNameUseCase: SaveUserNameUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<ProfileState> =
        MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    private val _steps: com.kocoukot.holdgame.core.SingleLiveEvent<ProfileRoute> =
        com.kocoukot.holdgame.core.SingleLiveEvent()
    val steps: com.kocoukot.holdgame.core.SingleLiveEvent<ProfileRoute> = _steps

    init {
        viewModelScope.launch {
            getUserNameUseCase.getName()?.userName?.let {
                _state.value = _state.value.copy(userNickname = it)
            }
        }
    }

    fun setInputActions(action: ProfileActions) {
        when (action) {
            ProfileActions.ClickOnBack -> goBack()
            is ProfileActions.ClickOnSaveNickname -> saveUsername(action.nickname)
        }

    }

    private fun goBack() {
        _steps.value = ProfileRoute.OnBack
    }

    private fun saveUsername(nickname: String) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            kotlin.runCatching { saveUserNameUseCase.saveName(nickname, false) }
                .onSuccess { _steps.value = ProfileRoute.OnBack }
                .onFailure {
                    _state.value =
                        _state.value.copy(errorText = it.localizedMessage ?: "Some error! =(")
                }
            _state.value = _state.value.copy(isLoading = false)
        }
    }
}

