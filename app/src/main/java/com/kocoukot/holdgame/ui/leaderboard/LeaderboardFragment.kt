package com.kocoukot.holdgame.ui.leaderboard

import LeaderboardScreeContent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.kocoukot.holdgame.common.ext.navController
import com.kocoukot.holdgame.ui.common.ext.observeNonNull
import com.kocoukot.holdgame.ui.leaderboard.model.LeaderboardRoute
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