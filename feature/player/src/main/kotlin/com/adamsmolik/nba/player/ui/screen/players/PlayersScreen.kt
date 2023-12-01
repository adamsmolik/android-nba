package com.adamsmolik.nba.player.ui.screen.players

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.adamsmolik.nba.core.ui.composable.AvatarMedium
import com.adamsmolik.nba.core.ui.composable.BaseStatefulScreen
import com.adamsmolik.nba.core.ui.composable.DefaultPreview
import com.adamsmolik.nba.core.ui.composable.DotDivider
import com.adamsmolik.nba.core.ui.composable.HorizontalSpacer
import com.adamsmolik.nba.core.ui.composable.VerticalSpacer
import com.adamsmolik.nba.core.ui.composable.paging.LazyColumnPaged
import com.adamsmolik.nba.core.ui.composable.paging.PagingUiModel
import com.adamsmolik.nba.core.ui.composable.paging.preview
import com.adamsmolik.nba.core.ui.theme.NBATheme
import com.adamsmolik.nba.domain.player.model.PlayerModel
import com.adamsmolik.nba.domain.player.model.mock
import com.adamsmolik.nba.feature.player.R
import com.adamsmolik.nba.player.LocalPlayerContract
import com.adamsmolik.nba.player.ui.model.PlayerUiModel
import com.adamsmolik.nba.player.ui.model.PlayersUiModel
import com.adamsmolik.nba.player.ui.model.toUiModel

@Immutable
private interface PlayersListener {
    fun onPlayerClick(player: PlayerUiModel)
}

@Composable
fun PlayersScreen(viewModel: PlayersViewModel) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    val activity = LocalActivity.current
    val contract = LocalPlayerContract.current

    val listener = remember(activity, contract) {
        object : PlayersListener {
            override fun onPlayerClick(player: PlayerUiModel) {
                contract.navigatePlayerDetail(activity, player.id)
            }
        }
    }

    BaseStatefulScreen(
        title = stringResource(id = R.string.players_title),
        state = state,
        onRetry = viewModel::loadData,
        appBarDivider = AppBarDividerType.Lazy(listState),
    ) { data ->
        PlayersContent(
            state = data,
            listState = listState,
            listener = listener,
        )
    }
}

@Composable
private fun PlayersContent(
    state: PlayersUiModel,
    listState: LazyListState,
    listener: PlayersListener,
) {
    LazyColumnPaged(
        paging = state.paging,
        state = listState,
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        navigationInsets = true,
    ) {
        items(state.players) { player ->
            Item(
                player = player,
                onClick = listener::onPlayerClick,
            )
        }
    }
}

@Composable
private fun Item(
    player: PlayerUiModel,
    onClick: (PlayerUiModel) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(NBATheme.colors.surface.secondary)
            .clickable { onClick(player) }
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp,
            ),
    ) {
        AvatarMedium(
            url = player.avatar,
        )

        HorizontalSpacer(16.dp)

        Column {
            Text(
                text = player.fullName,
                color = NBATheme.colors.onSurface.primary,
                style = NBATheme.typography.title.large,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            VerticalSpacer(2.dp)

            Row {
                Text(
                    text = player.teamName,
                    color = NBATheme.colors.onSurface.secondary,
                    style = NBATheme.typography.body.medium,
                )

                if (player.position != null) {
                    DotDivider(
                        style = NBATheme.typography.body.medium,
                        color = NBATheme.colors.onSurface.secondary,
                    )

                    Text(
                        text = player.position,
                        color = NBATheme.colors.onSurface.secondary,
                        style = NBATheme.typography.body.medium,
                    )
                }
            }
        }
    }
}

@Preview(name = "PlayersContent", group = "Feature Account")
@Composable
fun PreviewPlayersContent() {
    DefaultPreview {
        PlayersContent(
            state = PlayersUiModel(
                players = List(10) {
                    PlayerModel.mock(id = it).toUiModel()
                },
                paging = PagingUiModel.preview(),
            ),
            listState = rememberLazyListState(),
            listener = object : PlayersListener {
                override fun onPlayerClick(player: PlayerUiModel) {}
            },
        )
    }
}
