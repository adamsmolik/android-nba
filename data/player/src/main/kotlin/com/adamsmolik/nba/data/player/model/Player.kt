// ktlint-disable filename

package com.adamsmolik.nba.data.player.model

import com.adamsmolik.nba.domain.player.model.PlayerModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayerDTO(
    val id: Int,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "last_name")
    val lastName: String,
    val position: String,
    val team: TeamDTO,
)

fun PlayerDTO.toModel() = PlayerModel(
    id = id,
    firstName = firstName,
    lastName = lastName,
    position = position.ifEmpty { null },
    team = team.toModel(),
)
