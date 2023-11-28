package com.adamsmolik.nba.player.ui.model

import androidx.compose.runtime.Immutable
import com.adamsmolik.nba.core.base.paging.PagingUiModel
import com.adamsmolik.nba.domain.player.model.PlayerModel

@Immutable
data class PlayersUiModel(
    val players: List<PlayerUiModel>,
    val paging: PagingUiModel,
)

@Immutable
data class PlayerUiModel(
    val id: String,
    val teamId: String,
    val firstName: String,
    val lastName: String,
    val position: String?,
    val teamName: String,
) {
    val fullName: String = "$firstName $lastName"
}

fun PlayerModel.toUiModel() = PlayerUiModel(
    id = id,
    teamId = team.id,
    firstName = firstName,
    lastName = lastName,
    position = position,
    teamName = team.name,
)
