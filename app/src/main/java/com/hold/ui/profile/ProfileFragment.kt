package com.hold.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.hold.ui.common.ext.navController
import com.hold.ui.common.ext.observeNonNull
import com.hold.ui.profile.model.ProfileRoute
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.steps.observeNonNull(viewLifecycleOwner) { route ->
            when (route) {
                ProfileRoute.OnBack -> navController.popBackStack()
            }
        }

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            setContent {
                ProfileScreeContent(viewModel)
            }
        }
    }
}