package com.example.benchmark

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalBaselineProfilesApi::class)
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val baselineRule = BaselineProfileRule()

    @Test
    fun generateBaselineProfile() = baselineRule.collectBaselineProfile(
        packageName = "com.example.composingradio"
    ){

        startActivityAndWait()

        val list = device.findObject(By.res("search_list"))

        device.waitForIdle()

        list.fling(Direction.DOWN)
        list.fling(Direction.DOWN)
        list.fling(Direction.DOWN)

    }


}