package com.adamsmolik.nba.base.arch

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.adamsmolik.nba.base.CoreConfig
import com.adamsmolik.nba.base.R
import com.adamsmolik.nba.base.theme.NBATheme
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {
    abstract val screen: @Composable () -> Unit

    abstract val viewModel: BaseViewModel

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
            NBATheme {
                screen()
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
