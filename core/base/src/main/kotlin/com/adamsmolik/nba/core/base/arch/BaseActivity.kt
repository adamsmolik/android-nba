package com.adamsmolik.nba.core.base.arch

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.view.WindowCompat
import com.adamsmolik.nba.core.base.CoreConfig
import com.adamsmolik.nba.core.base.R
import com.adamsmolik.nba.core.base.composable.noLocalProviderFor
import com.adamsmolik.nba.core.base.error.ErrorHandler
import com.adamsmolik.nba.core.base.error.LocalErrorHandler
import com.adamsmolik.nba.core.base.theme.NBATheme
import javax.inject.Inject
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {
    abstract val screen: @Composable () -> Unit

    abstract val viewModel: BaseViewModel

    @Inject
    protected lateinit var errorHandler: ErrorHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d(this.javaClass.name)
        super.onCreate(savedInstanceState)

        handleOrientation()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setupScreen()
    }

    override fun onStart() {
        super.onStart()
        screenView()
    }

    override fun onNewIntent(intent: Intent) {
        Timber.d(this.javaClass.name)
        super.onNewIntent(intent)
    }

    override fun onDestroy() {
        Timber.d(this.javaClass.name)
        super.onDestroy()
    }

    abstract fun screenView()

    private fun setupScreen() {
        setContent {
            CompositionLocalProvider(
                LocalActivity provides this,
                LocalErrorHandler provides errorHandler,
            ) {
                NBATheme {
                    screen()
                }
            }
        }
    }

    private fun handleOrientation() {
        val isTablet = resources.getBoolean(R.bool.is_tablet)
        requestedOrientation = if (isTablet || CoreConfig.DEVELOP_FLAVOR) {
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}

val LocalActivity: ProvidableCompositionLocal<BaseActivity> = staticCompositionLocalOf {
    noLocalProviderFor("LocalActivity")
}
