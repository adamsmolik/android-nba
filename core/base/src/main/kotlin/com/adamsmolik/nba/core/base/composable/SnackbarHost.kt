package com.adamsmolik.nba.core.base.composable

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adamsmolik.nba.core.base.arch.LocalActivity
import com.adamsmolik.nba.core.base.theme.LightRippleTheme
import com.adamsmolik.nba.core.base.theme.NBATheme
import com.adamsmolik.nba.core.base.util.TextRes
import com.adamsmolik.nba.core.base.util.asString

sealed interface Snackbar {

    val message: TextRes
    val duration: SnackbarDuration

    data class Message(
        override val message: TextRes,
        override val duration: SnackbarDuration = SnackbarDuration.Short,
    ) : Snackbar

    data class Action(
        override val message: TextRes,
        override val duration: SnackbarDuration = SnackbarDuration.Short,
        val actionTitle: TextRes,
        @DrawableRes
        val actionIcon: Int?,
        val actionCallback: () -> Unit,
    ) : Snackbar
}

class SnackbarHandler(
    private val activity: Activity,
) {
    internal val snackbarHostState by lazy { SnackbarHostState() }

    suspend fun showSnackbar(snackbar: Snackbar) {
        when (snackbar) {
            is Snackbar.Message -> {
                snackbarHostState.showSnackbar(
                    CustomSnackbarVisuals(
                        message = snackbar.message.asString(activity),
                        duration = snackbar.duration,
                    ),
                )
            }
            is Snackbar.Action -> {
                val result = snackbarHostState.showSnackbar(
                    CustomSnackbarVisuals(
                        message = snackbar.message.asString(activity),
                        duration = snackbar.duration,
                        actionLabel = snackbar.actionTitle.asString(activity),
                        actionIcon = snackbar.actionIcon,
                    ),
                )
                when (result) {
                    SnackbarResult.Dismissed -> {}
                    SnackbarResult.ActionPerformed -> snackbar.actionCallback()
                }
            }
        }
    }
}

@Composable
fun rememberSnackbarHandler(): SnackbarHandler {
    val activity = LocalActivity.current

    return remember(activity) {
        SnackbarHandler(
            activity = activity,
        )
    }
}

class CustomSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = false,
    @DrawableRes
    val actionIcon: Int? = null,
) : SnackbarVisuals

@Composable
fun SnackbarHost(
    snackbarHandler: SnackbarHandler,
    modifier: Modifier = Modifier,
) {
    androidx.compose.material3.SnackbarHost(
        hostState = snackbarHandler.snackbarHostState,
        modifier = modifier
            .padding(12.dp),
        snackbar = { snackbarData ->
            val snackbarVisuals = snackbarData.visuals as CustomSnackbarVisuals
            val shape = RoundedCornerShape(8.dp)

            CompositionLocalProvider(LocalRippleTheme provides LightRippleTheme) {
                Snackbar(
                    content = {
                        SnackbarContent(
                            message = snackbarVisuals.message,
                        )
                    },
                    action = if (snackbarVisuals.actionLabel != null) {
                        {
                            SnackbarAction(
                                title = snackbarVisuals.actionLabel,
                                icon = snackbarVisuals.actionIcon,
                                onClick = { snackbarData.performAction() },
                            )
                        }
                    } else {
                        null
                    },
                    shape = shape,
                    containerColor = NBATheme.colors.surface.inverse,
                    contentColor = NBATheme.colors.onSurface.inverse,
                    actionContentColor = NBATheme.colors.onSurface.inverse,
                    modifier = Modifier
                        .clip(shape)
                        .clickable { snackbarData.dismiss() },
                )
            }
        },
    )
}

@Composable
private fun SnackbarContent(
    message: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 8.dp),
    ) {
        Text(
            text = message,
            style = NBATheme.typography.body.medium,
        )
    }
}

@Composable
private fun SnackbarAction(
    title: String,
    @DrawableRes
    icon: Int?,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(
                vertical = 8.dp,
                horizontal = 8.dp,
            ),
    ) {
        if (icon != null) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp),
            )

            Spacer(4.dp)
        }

        Text(
            text = title.toUpperCase(Locale.current),
            style = NBATheme.typography.label.small,
        )
    }
}

@Preview(name = "Snackbar", group = "Core Base")
@Composable
fun PreviewSnackbarContent() {
    DefaultPreview {
        SnackbarContent(
            message = "Snackbar message",
        )
    }
}

@Preview(name = "SnackbarAction", group = "Core Base")
@Composable
fun PreviewSnackbarAction() {
    DefaultPreview {
        SnackbarAction(
            title = "Retry",
            icon = android.R.drawable.ic_menu_revert,
            onClick = {},
        )
    }
}
