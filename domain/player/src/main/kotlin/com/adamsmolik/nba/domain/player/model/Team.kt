// ktlint-disable filename

package com.adamsmolik.nba.domain.player.model

data class TeamModel(
    val id: Int,
    val name: String,
) {
    companion object
}

fun TeamModel.Companion.mock(
    id: Int = 0,
    name: String = "Lakers",
) = TeamModel(
    id = id,
    name = name,
)
