// ktlint-disable filename

package com.adamsmolik.nba.data.player.model

import com.adamsmolik.nba.domain.player.model.PlayerDetailModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayerDetailDTO(
    val id: Int,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "last_name")
    val lastName: String,
    val position: String,
    @Json(name = "height_feet")
    val heightFeet: Int?,
    @Json(name = "height_inches")
    val heightInches: Int?,
    @Json(name = "weight_pounds")
    val weightPounds: Int?,
    val team: TeamDTO,
)

fun PlayerDetailDTO.toModel() = PlayerDetailModel(
    id = id,
    firstName = firstName,
    lastName = lastName,
    avatar = "https://picsum.photos/id/$id/400",
    position = position.ifEmpty { null },
    heightFeet = heightFeet,
    heightInches = heightInches,
    weightPounds = weightPounds,
    team = team.toModel(),
)
