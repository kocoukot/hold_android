package com.hold.ui.common.ext

import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

fun drawableTarget(onLoadCleared: (Drawable?) -> Unit = {}, onResourceReady: (Drawable) -> Unit) =
    object : CustomTarget<Drawable>() {
        override fun onResourceReady(
            resource: Drawable,
            transition: Transition<in Drawable>?,
        ) {
            onResourceReady.invoke(resource)
        }

        override fun onLoadCleared(placeholder: Drawable?) {
            onLoadCleared.invoke(placeholder)
        }
    }
