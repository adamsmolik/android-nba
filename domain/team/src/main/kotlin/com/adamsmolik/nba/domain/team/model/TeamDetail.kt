// ktlint-disable filename

package com.adamsmolik.nba.domain.team.model

data class TeamDetailModel(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val fullName: String,
    val name: String,
) {
    companion object
}

fun TeamDetailModel.Companion.mock(
    id: Int = 0,
    abbreviation: String = "LAL",
    city: String = "Los Angeles",
    conference: String = "West",
    division: String = "Pacific",
    fullName: String = "Los Angeles Lakers",
    name: String = "Lakers",
) = TeamDetailModel(
    id = id,
    abbreviation = abbreviation,
    city = city,
    conference = conference,
    division = division,
    fullName = fullName,
    name = name,
)
