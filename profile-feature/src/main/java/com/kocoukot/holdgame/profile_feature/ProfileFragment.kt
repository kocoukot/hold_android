package com.kocoukot.holdgame.profile_feature

import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import com.kocoukot.holdgame.core_mvi.BaseFragment
import com.kocoukot.holdgame.navController
import com.kocoukot.holdgame.profile_feature.model.ProfileRoute
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<ProfileViewModel>(
) {
    override val viewModel: ProfileViewModel by viewModel()
    override val screenContent: @Composable (ProfileViewModel) -> Unit =
        @Composable { ProfileScreeContent(viewModel) }

    override fun observeData() {
        viewModel.observeSteps().onEach { route ->
            when (route) {
                ProfileRoute.OnBack -> navController.popBackStack()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}