package com.kocoukot.holdgame.core_mvi

interface Mapper<S, R> {

    fun map(data: S): R

    interface Unit<T> : Mapper<T, kotlin.Unit>
}