// ktlint-disable filename

package com.adamsmolik.nba.domain.player.model

data class PlayerDetailModel(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val position: String?,
    val heightFeet: Int?,
    val heightInches: Int?,
    val weightPounds: Int?,
    val team: TeamModel,
) {
    companion object
}

fun PlayerDetailModel.Companion.mock(
    id: Int = 0,
    firstName: String = "LeBron",
    lastName: String = "James",
    position: String? = "F",
    heightFeet: Int = 6,
    heightInches: Int = 8,
    weightPounds: Int = 250,
    team: TeamModel = TeamModel.mock(),
) = PlayerDetailModel(
    id = id,
    firstName = firstName,
    lastName = lastName,
    position = position,
    heightFeet = heightFeet,
    heightInches = heightInches,
    weightPounds = weightPounds,
    team = team,
)
