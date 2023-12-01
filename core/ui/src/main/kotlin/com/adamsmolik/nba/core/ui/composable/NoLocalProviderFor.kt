package com.adamsmolik.nba.core.ui.composable

fun noLocalProviderFor(name: String): Nothing {
    error("CompositionLocal $name not provided")
}
