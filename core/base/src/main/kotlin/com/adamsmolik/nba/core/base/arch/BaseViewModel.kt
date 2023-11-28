package com.adamsmolik.nba.core.base.arch

import androidx.lifecycle.ViewModel
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

open class BaseViewModel : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main.immediate + job

    init {
        Timber.d(this.javaClass.name)
    }

    override fun onCleared() {
        job.cancel()
        Timber.d(this.javaClass.name)
        super.onCleared()
    }
}

open class BaseStatefulViewModel<T : Any>(defaultState: ScreenState<T> = ScreenState.Progress) :
    BaseViewModel(),
    ScreenStateHandler<T> by ScreenStateHandlerImpl(
        defaultState,
    )
