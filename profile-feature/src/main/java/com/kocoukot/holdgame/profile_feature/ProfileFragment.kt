package com.kocoukot.holdgame.profile_feature

import androidx.compose.runtime.Composable
import com.kocoukot.holdgame.core_mvi.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<ProfileViewModel>(
) {
    override val viewModel: ProfileViewModel by viewModel()
    override val screenContent: @Composable (ProfileViewModel) -> Unit =
        @Composable { ProfileScreeContent(viewModel) }
}