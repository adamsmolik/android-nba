// ktlint-disable filename

package com.adamsmolik.nba.domain.player.model

data class PlayerModel(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val position: String?,
    val team: TeamModel,
) {
    companion object
}

fun PlayerModel.Companion.mock(
    id: Int = 0,
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
