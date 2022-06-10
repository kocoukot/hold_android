package com.hold.ui.common.ext

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Pattern


private const val phoneFormat = "%s (%s) %s-%s"

fun CharSequence?.isEmail() =
    !this.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun CharSequence?.formatAsPhoneNumber(): CharSequence? =
    this?.let {
        val rawString = if (it.startsWith("+")) it else "+$it"
        runCatching {
            phoneFormat.format(
                rawString.substring(0, 2),
                rawString.substring(2, 5),
                rawString.substring(5, 8),
                rawString.substring(8, 12)
            )
        }.getOrDefault(it)
    }

fun CharSequence?.isValidMobile(): Boolean {
    return if (!Pattern.matches("[a-zA-Z]+", this)) {
        this?.length == 17
    } else false
}

fun CharSequence?.isValidPhoneNumber(): Boolean {
    return if (!TextUtils.isEmpty(this)) {
        Patterns.PHONE.matcher(this).matches()
    } else false
}






