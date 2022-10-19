package com.kocoukot.holdgame.profile_feature

import androidx.lifecycle.viewModelScope
import com.kocoukot.holdgame.core_mvi.BaseViewModel
import com.kocoukot.holdgame.domain.usecase.user.GetUserNameUseCase
import com.kocoukot.holdgame.domain.usecase.user.SaveUserNameUseCase
import com.kocoukot.holdgame.profile_feature.model.ProfileActions
import com.kocoukot.holdgame.profile_feature.model.ProfileRoute
import com.kocoukot.holdgame.profile_feature.model.ProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val saveUserNameUseCase: SaveUserNameUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
) : BaseViewModel.Base<ProfileState>(
    mState = MutableStateFlow(ProfileState())
) {

    init {
        viewModelScope.launch {
            getUserNameUseCase.getName()?.userName?.let { userName ->
                updateInfo { copy(userNickname = userName) }
            }
        }
    }

    fun setInputActions(action: ProfileActions) {
        when (action) {
            ProfileActions.ClickOnBack -> sendEvent(ProfileRoute.OnBack)
            is ProfileActions.ClickOnSaveNickname -> saveUsername(action.nickname)
        }
    }

    private fun saveUsername(nickname: String) {
        updateInfo { copy(isLoading = true) }
        viewModelScope.launch {
            kotlin.runCatching { saveUserNameUseCase.saveName(nickname, false) }
                .onSuccess { sendEvent(ProfileRoute.OnBack) }
                .onFailure {
                    updateInfo { copy(errorText = it.localizedMessage ?: "Some error! =(") }
                }
            updateInfo { copy(isLoading = false) }
        }
    }
}

