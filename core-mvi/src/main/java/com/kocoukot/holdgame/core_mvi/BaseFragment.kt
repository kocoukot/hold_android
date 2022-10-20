package com.kocoukot.holdgame.core_mvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kocoukot.holdgame.navController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {
    protected abstract val screenContent: @Composable (VM) -> Unit
    protected abstract val viewModel: VM

    protected open fun observeData(cb: ((ComposeRoute) -> Unit)? = null) {
        viewModel.observeSteps().onEach { route ->
            when (route) {
                is ComposeRouteFinishApp -> requireActivity().finish()
                is ComposeRouteNavigation -> navigateScreen(route)
                is ComposeRoutePermission -> checkPermissions()
                else -> cb?.invoke(route)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun checkPermissions() {
        TODO("Not yet implemented")
    }

    fun navigateScreen(screen: ComposeRouteNavigation) {
        navController.apply {
            screen.destination?.let { dest ->
                navigate(dest)
            } ?: kotlin.run {
                popBackStack()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeData()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )
            setContent {
                screenContent.invoke(viewModel)
            }
        }
    }
}