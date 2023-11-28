package com.adamsmolik.nba.core.base.composable

fun noLocalProviderFor(name: String): Nothing {
    error("CompositionLocal $name not provided")
}
