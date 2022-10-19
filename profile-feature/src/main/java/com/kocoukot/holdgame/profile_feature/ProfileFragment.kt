package com.kocoukot.holdgame.profile_feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.lifecycleScope
import com.kocoukot.holdgame.core_mvi.BaseFragment
import com.kocoukot.holdgame.navController
import com.kocoukot.holdgame.profile_feature.model.ProfileRoute
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<ProfileViewModel>() {
    override val viewModel: ProfileViewModel by viewModel()


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
                ProfileScreeContent(viewModel)
            }
        }
    }

    override fun observeData() {

        viewModel.steps.onEach { route ->
            when (route) {
                ProfileRoute.OnBack -> navController.popBackStack()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }


}