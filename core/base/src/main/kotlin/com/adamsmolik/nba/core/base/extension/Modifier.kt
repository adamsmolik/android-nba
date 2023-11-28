package com.adamsmolik.nba.core.base.extension

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier

fun Modifier.navigationInsets(navigationInset: Boolean) =
    if (navigationInset) {
        navigationBarsPadding()
    } else {
        this
    }

fun Modifier.verticalScroll(scrollState: ScrollState?) =
    if (scrollState != null) {
        verticalScroll(state = scrollState)
    } else {
        this
    }
