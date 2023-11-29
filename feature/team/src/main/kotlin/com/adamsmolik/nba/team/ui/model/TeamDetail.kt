package com.adamsmolik.nba.team.ui.model

import androidx.compose.runtime.Immutable
import com.adamsmolik.nba.domain.team.model.TeamDetailModel

@Immutable
data class TeamDetailUiModel(
    val id: Int,
    val fullName: String,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
)

fun TeamDetailModel.toUiModel() = TeamDetailUiModel(
    id = id,
    fullName = fullName,
    abbreviation = abbreviation,
    city = city,
    conference = conference,
    division = division,
)
