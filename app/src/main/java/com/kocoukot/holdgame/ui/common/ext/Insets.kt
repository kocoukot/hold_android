package com.kocoukot.holdgame.ui.common.ext

import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsAnimation
import androidx.annotation.RequiresApi
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding

private fun getVerticalOffsets(insets: WindowInsetsCompat, rect: Rect): Pair<Int, Int> =
    with(insets) {
        val offsetTop = rect.top + getInsets(WindowInsetsCompat.Type.systemBars()).top
        val insetsBottom = getInsets(WindowInsetsCompat.Type.ime()).bottom
            .takeIf { it > 0 }
            ?: getInsets(WindowInsetsCompat.Type.systemBars()).bottom
        val offsetBottom = rect.bottom + insetsBottom

        offsetTop to offsetBottom
    }

fun View.doOnApplyWindowInsets(block: (View, WindowInsetsCompat, Rect) -> WindowInsetsCompat) {
    val initialPadding = Rect(paddingLeft, paddingTop, paddingRight, paddingBottom)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        block(v, insets, initialPadding)
    }
    requestApplyInsetsWhenAttached()
}

@RequiresApi(Build.VERSION_CODES.R)
fun View.doOnApplyWindowInsetsAnimated(block: (View, WindowInsets, Rect) -> WindowInsets) {
    val initialPadding = Rect(paddingLeft, paddingTop, paddingRight, paddingBottom)
    setWindowInsetsAnimationCallback(object : WindowInsetsAnimation.Callback(DISPATCH_MODE_STOP) {
        override fun onProgress(
            insets: WindowInsets,
            animations: MutableList<WindowInsetsAnimation>,
        ): WindowInsets = insets.also {
            block.invoke(this@doOnApplyWindowInsetsAnimated, it, initialPadding)
        }
    })
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        ViewCompat.requestApplyInsets(this)
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(view: View) {
                view.removeOnAttachStateChangeListener(this)
                ViewCompat.requestApplyInsets(view)
            }

            override fun onViewDetachedFromWindow(v: View) = removeOnAttachStateChangeListener(this)
        })
    }
}

fun WindowInsetsCompat.ignoreInsets(typeMask: Int) = let {
    WindowInsetsCompat.Builder(it)
        .setInsets(typeMask, Insets.of(0, 0, 0, 0))
        .apply {
            if (typeMask == WindowInsetsCompat.Type.systemBars()) {
                val imeInsets = it.getInsets(WindowInsetsCompat.Type.ime())
                setInsets(
                    WindowInsetsCompat.Type.ime(),
                    Insets.of(
                        imeInsets.left,
                        imeInsets.top,
                        imeInsets.right,
                        imeInsets.bottom - it.getInsets(typeMask).bottom
                    )
                )
            }
        }
        .build()
}

fun View.applyPaddingInsets(
    insets: WindowInsetsCompat,
    rect: Rect = Rect(),
    top: Boolean = false,
    bottom: Boolean = false,
): WindowInsetsCompat = insets.let {
    val (offsetTop, offsetBottom) = getVerticalOffsets(it, rect)
    updatePadding(
        top = if (top) offsetTop else rect.top,
        bottom = if (bottom) offsetBottom else rect.bottom
    )
    WindowInsetsCompat.CONSUMED
}

fun View.applyMarginInsets(
    insets: WindowInsetsCompat,
    rect: Rect = Rect(),
    top: Boolean = false,
    bottom: Boolean = false,
): WindowInsetsCompat = insets.let {
    val (offsetTop, offsetBottom) = getVerticalOffsets(it, rect)
    layoutParams
        .takeIf { it is ViewGroup.MarginLayoutParams }
        ?.let {
            updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = if (top) offsetTop else rect.top
                bottomMargin = if (bottom) offsetBottom else rect.bottom
            }
        }
    WindowInsetsCompat.CONSUMED
}