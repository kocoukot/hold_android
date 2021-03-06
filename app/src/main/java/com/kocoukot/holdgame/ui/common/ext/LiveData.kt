package com.kocoukot.holdgame.ui.common.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

inline fun <T> LiveData<T>.observeNonNull(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit,
) {
    this.observe(owner, {
        if (it != null) {
            observer(it)
        }
    })
}
