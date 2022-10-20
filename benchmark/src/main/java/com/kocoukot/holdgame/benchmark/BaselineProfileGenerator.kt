package com.kocoukot.holdgame.benchmark

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalBaselineProfilesApi
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun startup() = baselineProfileRule.collectBaselineProfile(
        packageName = "com.kocoukot.holdgame"
    ) {
        pressHome()
        startActivityAndWait()

//          val leaderButton = device.findObject(By.res("leader_btn"))
//          leaderButton.click()

        // Wait until the screen is gone = the detail is shown
//          device.wait(Until.gone(By.res("leader_btn")), 5_000)
    }
}