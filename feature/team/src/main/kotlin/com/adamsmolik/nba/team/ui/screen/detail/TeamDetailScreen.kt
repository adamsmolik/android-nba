package com.adamsmolik.nba.team.ui.screen.detail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.adamsmolik.nba.core.ui.composable.AppBarDividerType
import com.adamsmolik.nba.core.ui.composable.BaseStatefulScreen
import com.adamsmolik.nba.core.ui.composable.DefaultPreview
import com.adamsmolik.nba.core.ui.composable.DotDivider
import com.adamsmolik.nba.core.ui.composable.InfoItem
import com.adamsmolik.nba.core.ui.composable.NavigationType
import com.adamsmolik.nba.core.ui.composable.VerticalSpacer
import com.adamsmolik.nba.core.ui.composable.rememberBackHandler
import com.adamsmolik.nba.core.ui.extension.verticalScroll
import com.adamsmolik.nba.core.ui.theme.NBATheme
import com.adamsmolik.nba.domain.team.model.TeamDetailModel
import com.adamsmolik.nba.domain.team.model.mock
import com.adamsmolik.nba.feature.team.R
import com.adamsmolik.nba.team.ui.model.TeamDetailUiModel
import com.adamsmolik.nba.team.ui.model.toUiModel

@Composable
fun TeamDetailScreen(viewModel: TeamDetailViewModel) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    val backHandler = rememberBackHandler()

    BaseStatefulScreen(
        title = stringResource(id = R.string.team_detail_title),
        state = state,
        onRetry = viewModel::loadData,
        navigationType = NavigationType.Clickable.Up(backHandler::navigateBack),
        appBarDivider = AppBarDividerType.Scroll(scrollState),
    ) { data ->
        TeamDetailContent(
            state = data,
            scrollState = scrollState,
        )
    }
}

@Composable
private fun TeamDetailContent(
    state: TeamDetailUiModel,
    scrollState: ScrollState,
) {
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .navigationBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .background(NBATheme.colors.surface.secondary)
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
        ) {
            Text(
                text = state.fullName,
                color = NBATheme.colors.onSurface.primary,
                style = NBATheme.typography.headline.medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            VerticalSpacer(size = 4.dp)

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = state.city,
                    color = NBATheme.colors.onSurface.secondary,
                    style = NBATheme.typography.body.large,
                )

                DotDivider(
                    style = NBATheme.typography.body.large,
                    color = NBATheme.colors.onSurface.secondary,
                )

                Text(
                    text = state.abbreviation,
                    color = NBATheme.colors.onSurface.secondary,
                    style = NBATheme.typography.body.large,
                )
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
                value = state.division,
                label = stringResource(id = R.string.team_detail_division),
            )

            InfoItem(
                value = state.conference,
                label = stringResource(id = R.string.team_detail_conference),
            )
        }
    }
}

@Preview(name = "TeamDetailContent", group = "Feature Account")
@Composable
fun PreviewTeamDetailContent() {
    DefaultPreview {
        TeamDetailContent(
            state = TeamDetailModel.mock().toUiModel(),
            scrollState = rememberScrollState(),
        )
    }
}
