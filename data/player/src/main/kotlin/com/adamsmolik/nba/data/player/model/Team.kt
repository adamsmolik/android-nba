// ktlint-disable filename

package com.adamsmolik.nba.data.player.model

import com.adamsmolik.nba.domain.player.model.TeamModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamDTO(
    val id: String,
    val name: String,
)

fun TeamDTO.toModel() = TeamModel(
    id = id,
    name = name,
)
