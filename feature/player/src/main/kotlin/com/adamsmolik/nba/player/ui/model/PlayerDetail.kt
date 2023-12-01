package com.adamsmolik.nba.player.ui.model

import androidx.compose.runtime.Immutable
import com.adamsmolik.nba.domain.player.model.PlayerDetailModel

@Immutable
data class PlayerDetailUiModel(
    val id: Int,
    val teamId: Int,
    val firstName: String,
    val lastName: String,
    val avatar: String,
    val position: String?,
    val heightFeet: Int?,
    val heightInches: Int?,
    val weightPounds: Int?,
    val teamName: String,
) {
    val fullName: String = "$firstName $lastName"
    val height: String? = if (heightFeet != null && heightInches != null) "$heightFeet′ $heightInches″" else null
    val weight: String? = if (weightPounds != null) "$weightPounds lb" else null
}

fun PlayerDetailModel.toUiModel() = PlayerDetailUiModel(
    id = id,
    teamId = team.id,
    firstName = firstName,
    lastName = lastName,
    avatar = avatar,
    position = position,
    heightFeet = heightFeet,
    heightInches = heightInches,
    weightPounds = weightPounds,
    teamName = team.name,
)
