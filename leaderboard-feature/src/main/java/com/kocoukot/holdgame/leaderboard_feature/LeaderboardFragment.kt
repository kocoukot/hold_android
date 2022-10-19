package com.kocoukot.holdgame.leaderboard_feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.lifecycleScope
import com.kocoukot.holdgame.core_mvi.BaseFragment
import com.kocoukot.holdgame.leaderboard_feature.model.LeaderboardRoute
import com.kocoukot.holdgame.navController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class LeaderboardFragment : BaseFragment<LeaderboardViewModel>() {
    override val viewModel: LeaderboardViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            setContent {
                LeaderboardScreeContent(viewModel)
            }
        }
    }

    override fun observeData() {
        viewModel.steps.onEach { route ->
            when (route) {
                LeaderboardRoute.OnBack -> navController.popBackStack()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}