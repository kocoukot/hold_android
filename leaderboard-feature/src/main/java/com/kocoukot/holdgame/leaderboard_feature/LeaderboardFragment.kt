package com.kocoukot.holdgame.leaderboard_feature

import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import com.kocoukot.holdgame.core_mvi.BaseFragment
import com.kocoukot.holdgame.leaderboard_feature.model.LeaderboardRoute
import com.kocoukot.holdgame.navController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class LeaderboardFragment : BaseFragment<LeaderboardViewModel>() {
    override val viewModel: LeaderboardViewModel by viewModel()

    override val screenContent: @Composable (LeaderboardViewModel) -> Unit =
        @Composable { LeaderboardScreeContent(viewModel) }

    override fun observeData() {
        viewModel.observeSteps().onEach { route ->
            when (route) {
                LeaderboardRoute.OnBack -> navController.popBackStack()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}