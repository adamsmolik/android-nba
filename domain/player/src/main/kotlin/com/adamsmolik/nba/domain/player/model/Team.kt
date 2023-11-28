// ktlint-disable filename

package com.adamsmolik.nba.domain.player.model

data class TeamModel(
    val id: String,
    val name: String,
) {
    companion object
}

fun TeamModel.Companion.mock(
    id: String = "",
    name: String = "Lakers",
) = TeamModel(
    id = id,
    name = name,
)
