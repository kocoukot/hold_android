package com.kocoukot.holdgame.leaderboard_feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.kocoukot.holdgame.leaderboard_feature.model.LeaderboardRoute
import com.kocoukot.holdgame.navController
import com.kocoukot.holdgame.observeNonNull
import org.koin.androidx.viewmodel.ext.android.viewModel

class LeaderboardFragment : Fragment() {
    private val viewModel: LeaderboardViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.steps.observeNonNull(viewLifecycleOwner) { route ->
            when (route) {
                LeaderboardRoute.OnBack -> navController.popBackStack()
            }
        }

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            setContent {
                LeaderboardScreeContent(viewModel)
            }
        }
    }
}