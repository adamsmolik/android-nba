// ktlint-disable filename

package com.adamsmolik.nba.data.team.model

import com.adamsmolik.nba.domain.team.model.TeamDetailModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamDetailDTO(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    @Json(name = "full_name")
    val fullName: String,
    val name: String,
)

fun TeamDetailDTO.toModel() = TeamDetailModel(
    id = id,
    abbreviation = abbreviation,
    city = city,
    conference = conference,
    division = division,
    fullName = fullName,
    name = name,
)
