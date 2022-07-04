package com.kocoukot.holdgame.ui.common

interface PasswordCheckStrategy {

    operator fun invoke(text: String): Boolean
}

object UpperCaseCheckStrategy : PasswordCheckStrategy {

    override fun invoke(text: String): Boolean = text.contains("[A-Z]+".toRegex())
}

object LowerCaseCheckStrategy : PasswordCheckStrategy {

    override fun invoke(text: String): Boolean = text.contains("[a-z]+".toRegex())
}

object LengthCheckStrategy : PasswordCheckStrategy {

    override fun invoke(text: String): Boolean = text.length >= 8
}

object OneDigitCheckStrategy : PasswordCheckStrategy {

    override fun invoke(text: String): Boolean = text.contains("[0-9]+".toRegex())
}
