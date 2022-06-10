package com.hold.common.ext

import android.content.res.Resources

fun Int.toDp(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Float.toDp(): Float = (this * Resources.getSystem().displayMetrics.density)
fun Int.addCommas(): String = "%,d".format(this)
