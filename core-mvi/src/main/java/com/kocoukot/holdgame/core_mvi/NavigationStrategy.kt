package com.kocoukot.holdgame.core_mvi

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kocoukot.holdgame.navController

interface NavigationStrategy {
    fun navigate(destination: Int, bundle: Bundle? = null)

//    abstract class Abstract(protected open val fragment: Fragment) : NavigationStrategy {
//        override fun navigate(
//            destination: Int,
//            bundle: Bundle?
//        ) {
//            fragment.navController.navigate(destination, bundle)
//        }
//    }

    data class NavigateForward(private val fragment: Fragment) : NavigationStrategy {
        override fun navigate(destination: Int, bundle: Bundle?) {
            fragment.navController.navigate(destination, bundle)
        }
    }


    data class Pop(private val fragment: Fragment) : NavigationStrategy {
        override fun navigate(destination: Int, bundle: Bundle?) {
            fragment.navController.popBackStack()
        }
    }
}