// ktlint-disable filename

package com.adamsmolik.nba.domain.player.model

data class PlayerModel(
    val id: String,
    val firstName: String,
    val lastName: String,
    val position: String?,
    val team: TeamModel,
) {
    companion object
}

fun PlayerModel.Companion.mock(
    id: String = "",
    firstName: String = "LeBron",
    lastName: String = "James",
    position: String? = "F",
    team: TeamModel = TeamModel.mock(),
) = PlayerModel(
    id = id,
    firstName = firstName,
    lastName = lastName,
    position = position,
    team = team,
)
