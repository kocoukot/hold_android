package com.hold.ui.endgame.ask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.hold.ui.common.ext.navController
import com.hold.ui.common.ext.observeNonNull
import com.hold.ui.leaderboard.model.LeaderboardRoute
import org.koin.androidx.viewmodel.ext.android.viewModel

class AskContinueFragment : Fragment() {
    private val viewModel: AskContinueViewModel by viewModel()


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
                AskContinueScreeContent(viewModel)
            }
        }
    }
}