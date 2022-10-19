package com.kocoukot.holdgame.data.local

import android.content.Context
import android.content.res.AssetManager
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class AssetsStorage(private val context: Context) {
    private val assetManager: AssetManager by lazy { context.assets }

    fun readFileAsText(fileName: String): Single<String> = Single
        .fromCallable {
            assetManager.open(fileName).bufferedReader()
                .use { it.readText() }
        }
        .subscribeOn(Schedulers.io())
}
