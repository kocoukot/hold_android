package com.hold.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hold.arch.common.livedata.SingleLiveEvent
import com.hold.domain.usecase.user.GetUserNameUseCase
import com.hold.domain.usecase.user.SaveUserNameUseCase
import com.hold.ui.profile.model.ProfileActions
import com.hold.ui.profile.model.ProfileRoute
import com.hold.ui.profile.model.ProfileState
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

    private val _steps: SingleLiveEvent<ProfileRoute> = SingleLiveEvent()
    val steps: SingleLiveEvent<ProfileRoute> = _steps

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
        viewModelScope.launch {
            saveUserNameUseCase.saveName(nickname)
            _steps.value = ProfileRoute.OnBack
        }
    }
}

