package com.hold.ui.common

import androidx.core.view.WindowInsetsCompat

interface WindowInsetsListener {

    fun onApplyWindowInsets(insets: WindowInsetsCompat): WindowInsetsCompat
}
