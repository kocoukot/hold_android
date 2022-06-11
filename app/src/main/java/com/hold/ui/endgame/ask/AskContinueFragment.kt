package com.hold.ui.endgame.ask

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.hold.ui.common.ext.navController
import com.hold.ui.common.ext.observeNonNull
import com.hold.ui.leaderboard.model.LeaderboardRoute
import org.koin.androidx.viewmodel.ext.android.viewModel

class AskContinueFragment : DialogFragment() {
    private val viewModel: AskContinueViewModel by viewModel()

    private var onItemSelected: ((Boolean) -> Unit)? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setStyle(STYLE_NO_FRAME, android.R.style.Theme_Black)

        }

    }

    companion object {
        private const val TAG = "fragment_loading"

        fun create(
            fragmentManager: FragmentManager,
            onOptionSelected: ((Boolean) -> Unit)? = null
        ) {
            with(fragmentManager) {
                AskContinueFragment()
                    .apply { onItemSelected = onOptionSelected }
                    .show(this, TAG).also {

                    }
            }
        }
    }
}