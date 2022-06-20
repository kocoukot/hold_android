package com.hold.ui.button

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hold.R
import com.hold.ui.button.model.ButtonRoute
import com.hold.ui.common.ext.navController
import com.hold.ui.common.ext.observeNonNull
import org.koin.androidx.viewmodel.ext.android.viewModel

class ButtonFragment : Fragment() {
    private val viewModel: ButtonViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.steps.observeNonNull(viewLifecycleOwner) { route ->
            when (route) {
                ButtonRoute.ToLeaderboard -> navController.navigate(R.id.action_buttonFragment_to_leaderboardFragment)
                ButtonRoute.ToProfile -> {}
                ButtonRoute.CloseApp -> requireActivity().finish()
            }
        }
        activity?.window?.apply {
            statusBarColor = ContextCompat.getColor(requireContext(), R.color.main_background)
            navigationBarColor = ContextCompat.getColor(requireContext(), R.color.main_background)
        }
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            setContent {
                MainButtonScreenContent(viewModel)
            }
        }
    }
}