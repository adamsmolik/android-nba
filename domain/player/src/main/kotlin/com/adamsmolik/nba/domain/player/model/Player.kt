// ktlint-disable filename

package com.adamsmolik.nba.domain.player.model

data class PlayerModel(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val avatar: String,
    val position: String?,
    val team: TeamModel,
) {
    companion object
}

fun PlayerModel.Companion.mock(
    id: Int = 0,
    firstName: String = "LeBron",
    lastName: String = "James",
    avatar: String = "https://picsum.photos/200",
    position: String? = "F",
    team: TeamModel = TeamModel.mock(),
) = PlayerModel(
    id = id,
    firstName = firstName,
    lastName = lastName,
    avatar = avatar,
    position = position,
    team = team,
)
