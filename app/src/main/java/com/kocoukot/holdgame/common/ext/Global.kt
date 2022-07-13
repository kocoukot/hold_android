package com.kocoukot.holdgame.common.ext

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

private val decimalFormat =
    DecimalFormat("#0.000000", DecimalFormatSymbols.getInstance(Locale.US))

@Suppress("UNCHECKED_CAST")
inline fun <reified T> Any?.castSafe(): T? = this as? T

@Suppress("UNCHECKED_CAST")
inline fun <reified T> Any.cast(): T = this as T


val Fragment.navController
    get() = findNavController()