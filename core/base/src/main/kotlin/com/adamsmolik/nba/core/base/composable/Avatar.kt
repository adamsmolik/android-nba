package com.adamsmolik.nba.core.base.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.adamsmolik.nba.core.base.R
import com.adamsmolik.nba.core.base.theme.NBATheme

@Composable
fun AvatarXXSmall(
    modifier: Modifier = Modifier,
    url: String? = null, // TODO remove when BE starts sending images
) {
    Avatar(
        url = url,
        size = avatarSizeXXSmall,
        modifier = modifier,
        placeholderPadding = placeholderPaddingXXSmall,
    )
}

@Composable
fun AvatarXSmall(
    modifier: Modifier = Modifier,
    url: String? = null, // TODO remove when BE starts sending images
) {
    Avatar(
        url = url,
        size = avatarSizeXSmall,
        modifier = modifier,
        placeholderPadding = placeholderPaddingXSmall,
    )
}

@Composable
fun AvatarSmall(
    modifier: Modifier = Modifier,
    url: String? = null, // TODO remove when BE starts sending images
) {
    Avatar(
        url = url,
        size = avatarSizeSmall,
        placeholderPadding = placeholderPaddingSmall,
        modifier = modifier,
    )
}

@Composable
fun AvatarMedium(
    modifier: Modifier = Modifier,
    url: String? = null, // TODO remove when BE starts sending images
) {
    Avatar(
        url = url,
        size = avatarSizeMedium,
        placeholderPadding = placeholderPaddingMedium,
        modifier = modifier,
    )
}

@Composable
fun AvatarLarge(
    modifier: Modifier = Modifier,
    url: String? = null, // TODO remove when BE starts sending images
) {
    Avatar(
        url = url,
        size = avatarSizeLarge,
        placeholderPadding = placeholderPaddingLarge,
        modifier = modifier,
    )
}

@Composable
fun AvatarXLarge(
    modifier: Modifier = Modifier,
    url: String? = null, // TODO remove when BE starts sending images
) {
    Avatar(
        url = url,
        size = avatarSizeXLarge,
        placeholderPadding = placeholderPaddingLarge,
        modifier = modifier,
    )
}

@Composable
fun AvatarXXLarge(
    modifier: Modifier = Modifier,
    url: String? = null, // TODO remove when BE starts sending images
) {
    Avatar(
        url = url,
        size = avatarSizeXXLarge,
        placeholderPadding = placeholderPaddingXXLarge,
        modifier = modifier,
    )
}

@Composable
fun Avatar(
    url: String?,
    size: Dp,
    placeholderPadding: Dp,
    modifier: Modifier = Modifier,
) {
    var placeholderVisible by remember { mutableStateOf(true) }

    Box(
        modifier = modifier
            .requiredSize(size)
            .clip(CircleShape)
            .background(NBATheme.colors.surface.tertiary),
    ) {
        if (placeholderVisible) {
            Icon(
                painter = painterResource(id = R.drawable.ic_placeholder_avatar),
                contentDescription = null,
                tint = NBATheme.colors.onSurface.secondary,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = placeholderPadding)
                    .align(Alignment.BottomCenter),
            )
        }

        AsyncImage(
            model = url,
            contentDescription = stringResource(R.string.content_description_user_avatar),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            onState = {
                placeholderVisible = when (it) {
                    is AsyncImagePainter.State.Empty,
                    is AsyncImagePainter.State.Loading,
                    is AsyncImagePainter.State.Error,
                    -> true
                    is AsyncImagePainter.State.Success -> false
                }
            },
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}

internal val avatarSizeXXSmall = 16.dp
internal val placeholderPaddingXXSmall = 2.dp
internal val avatarSizeXSmall = 24.dp
internal val placeholderPaddingXSmall = 4.dp
internal val avatarSizeSmall = 32.dp
internal val placeholderPaddingSmall = 6.dp
internal val avatarSizeMedium = 48.dp
internal val placeholderPaddingMedium = 8.dp
internal val avatarSizeLarge = 64.dp
internal val avatarSizeXLarge = 80.dp
internal val placeholderPaddingLarge = 10.dp
internal val avatarSizeXXLarge = 104.dp
internal val placeholderPaddingXXLarge = 16.dp

@Preview(name = "AvatarXXSmall", group = "Core Base")
@Composable
fun PreviewAvatarXXSmall() {
    DefaultPreview {
        AvatarXXSmall()
    }
}

@Preview(name = "AvatarXSmall", group = "Core Base")
@Composable
fun PreviewAvatarXSmall() {
    DefaultPreview {
        AvatarXSmall()
    }
}

@Preview(name = "AvatarSmall", group = "Core Base")
@Composable
fun PreviewAvatarSmall() {
    DefaultPreview {
        AvatarSmall()
    }
}

@Preview(name = "AvatarMedium", group = "Core Base")
@Composable
fun PreviewAvatarMedium() {
    DefaultPreview {
        AvatarMedium()
    }
}

@Preview(name = "AvatarLarge", group = "Core Base")
@Composable
fun PreviewAvatarLarge() {
    DefaultPreview {
        AvatarLarge()
    }
}

@Preview(name = "AvatarXLarge", group = "Core Base")
@Composable
fun PreviewAvatarXLarge() {
    DefaultPreview {
        AvatarXLarge()
    }
}

@Preview(name = "AvatarXXLarge", group = "Core Base")
@Composable
fun PreviewAvatarXXLargeLarge() {
    DefaultPreview {
        AvatarXXLarge()
    }
}
