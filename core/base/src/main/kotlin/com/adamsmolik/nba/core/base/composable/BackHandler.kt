package com.adamsmolik.nba.core.base.composable

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

interface BackHandler {
    fun navigateBack()
}

@Composable
fun rememberBackHandler(): BackHandler {
    val backPressDispatcher = LocalOnBackPressedDispatcherOwner.current
    return remember(backPressDispatcher) {
        object : BackHandler {
            override fun navigateBack() {
                backPressDispatcher?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }
}
