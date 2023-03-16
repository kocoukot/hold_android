package com.kocoukot.holdgame.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.kocoukot.holdgame.castSafe
import com.kocoukot.holdgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    private val navHost by lazy {
        supportFragmentManager.findFragmentById(binding.navHostView.id)
            .castSafe<NavHostFragment>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivityMainBinding.inflate(layoutInflater)
                .also { binding = it }
                .root
        )
    }
}