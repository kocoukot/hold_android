package com.kocoukot.holdgame.common.ext

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.kocoukot.holdgame.R

fun NavController.navigateToRoot(
    @IdRes rootScreen: Int,
    @IdRes vararg backStack: Int = intArrayOf(),
) {
    backStack.forEachIndexed { index, screen ->
        if (index == 0) {
            navigate(
                screen, null,
                NavOptions
                    .Builder()
                    .setPopUpTo(R.id.nav_graph, false)
                    .build()
            )
        } else {
            navigate(screen)
        }
    }
    navigate(
        rootScreen, null,
        NavOptions.Builder()
            .setPopUpTo(backStack.lastOrNull() ?: R.id.nav_graph, false)
            .build()
    )
}