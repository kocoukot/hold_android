package com.kocoukot.holdgame.leaderboard_feature

import androidx.compose.runtime.Composable
import com.kocoukot.holdgame.core_mvi.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LeaderboardFragment : BaseFragment<LeaderboardViewModel>() {
    override val viewModel: LeaderboardViewModel by viewModel()

    override val screenContent: @Composable (LeaderboardViewModel) -> Unit =
        @Composable { LeaderboardScreeContent(viewModel) }
}