package com.kocoukot.holdgame.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.kocoukot.holdgame.common.ext.castSafe
import com.kocoukot.holdgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    private val navHost by lazy {
        supportFragmentManager.findFragmentById(binding.navHostView.id)
            .castSafe<NavHostFragment>()
    }

    private val currentVisibleFragment: Fragment?
        get() = navHost?.let {
            it.childFragmentManager.fragments.first()
        }

    private val onBackStackChangedListener by lazy {
        FragmentManager.OnBackStackChangedListener {

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivityMainBinding.inflate(layoutInflater)
                .also { binding = it }
                .root
        )
        navHost?.apply {
            childFragmentManager.addOnBackStackChangedListener(onBackStackChangedListener)
        }
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() = with(binding) {

        navHost?.let {

        }
    }
}