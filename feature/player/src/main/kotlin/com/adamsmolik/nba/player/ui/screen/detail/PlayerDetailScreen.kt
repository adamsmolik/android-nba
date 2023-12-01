package com.adamsmolik.nba.player.ui.screen.detail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.adamsmolik.nba.core.ui.arch.LocalActivity
import com.adamsmolik.nba.core.ui.composable.AppBarDividerType
import com.adamsmolik.nba.core.ui.composable.AvatarXXLarge
import com.adamsmolik.nba.core.ui.composable.BaseStatefulScreen
import com.adamsmolik.nba.core.ui.composable.DefaultPreview
import com.adamsmolik.nba.core.ui.composable.DotDivider
import com.adamsmolik.nba.core.ui.composable.InfoItem
import com.adamsmolik.nba.core.ui.composable.NavigationType
import com.adamsmolik.nba.core.ui.composable.VerticalSpacer
import com.adamsmolik.nba.core.ui.composable.rememberBackHandler
import com.adamsmolik.nba.core.ui.extension.verticalScroll
import com.adamsmolik.nba.core.ui.theme.NBATheme
import com.adamsmolik.nba.domain.player.model.PlayerDetailModel
import com.adamsmolik.nba.domain.player.model.mock
import com.adamsmolik.nba.feature.player.R
import com.adamsmolik.nba.player.LocalPlayerContract
import com.adamsmolik.nba.player.ui.model.PlayerDetailUiModel
import com.adamsmolik.nba.player.ui.model.toUiModel

@Immutable
private interface PlayerDetailListener {
    fun onTeamClick(id: Int)
}

@Composable
fun PlayerDetailScreen(viewModel: PlayerDetailViewModel) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    val activity = LocalActivity.current
    val contract = LocalPlayerContract.current
    val backHandler = rememberBackHandler()

    val listener = remember(activity, contract) {
        object : PlayerDetailListener {
            override fun onTeamClick(id: Int) = contract.navigateTeamDetail(activity, id)
        }
    }

    BaseStatefulScreen(
        title = stringResource(id = R.string.player_detail_title),
        state = state,
        onRetry = viewModel::loadData,
        navigationType = NavigationType.Clickable.Up(backHandler::navigateBack),
        appBarDivider = AppBarDividerType.Scroll(scrollState),
    ) { data ->
        PlayerDetailContent(
            state = data,
            scrollState = scrollState,
            listener = listener,
        )
    }
}

@Composable
private fun PlayerDetailContent(
    state: PlayerDetailUiModel,
    scrollState: ScrollState,
    listener: PlayerDetailListener,
) {
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .navigationBarsPadding(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(NBATheme.colors.surface.secondary)
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
        ) {
            AvatarXXLarge(
                url = state.avatar,
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
            ) {
                Text(
                    text = state.fullName,
                    color = NBATheme.colors.onSurface.primary,
                    style = NBATheme.typography.headline.medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = state.teamName,
                        color = NBATheme.colors.brand.blue3,
                        style = NBATheme.typography.body.large,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .clickable { listener.onTeamClick(state.teamId) }
                            .padding(
                                vertical = 4.dp,
                            ),
                    )

                    if (state.position != null) {
                        DotDivider(
                            style = NBATheme.typography.body.large,
                            color = NBATheme.colors.onSurface.secondary,
                        )

                        Text(
                            text = state.position,
                            color = NBATheme.colors.onSurface.secondary,
                            style = NBATheme.typography.body.large,
                        )
                    }
                }
            }
        }

        VerticalSpacer(size = 16.dp)

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(
                horizontal = 16.dp,
            ),
        ) {
            InfoItem(
                value = state.height,
                label = stringResource(id = R.string.player_detail_height),
            )

            InfoItem(
                value = state.weight,
                label = stringResource(id = R.string.player_detail_weight),
            )
        }
    }
}

@Preview(name = "PlayerDetailContent", group = "Feature Account")
@Composable
fun PreviewPlayerDetailContent() {
    DefaultPreview {
        PlayerDetailContent(
            state = PlayerDetailModel.mock().toUiModel(),
            scrollState = rememberScrollState(),
            listener = object : PlayerDetailListener {
                override fun onTeamClick(id: Int) {}
            },
        )
    }
}
