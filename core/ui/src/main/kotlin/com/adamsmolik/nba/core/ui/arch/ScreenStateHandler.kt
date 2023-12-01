package com.adamsmolik.nba.core.ui.arch

import com.adamsmolik.nba.core.base.arch.ResultState
import com.adamsmolik.nba.core.base.error.AppError
import com.adamsmolik.nba.core.ui.composable.paging.PagedState
import com.adamsmolik.nba.core.ui.composable.paging.PagingUiModel
import com.adamsmolik.nba.core.ui.composable.paging.toScreenState
import com.adamsmolik.nba.core.ui.composable.paging.toUiModel
import com.adamsmolik.nba.core.ui.extension.toScreenState
import com.adamsmolik.nba.core.ui.util.TextRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface ScreenStateHandler<T : Any> {
    val screenState: StateFlow<ScreenState<T>>
    val contentState: T?
    fun emitProgress()
    fun emitContent(state: T)
    fun emitEmpty(customMessage: TextRes? = null)
    fun emitError(error: AppError, customMessage: TextRes? = null)
    fun emitOffline()
    fun updateContent(update: (state: T) -> T)
    fun emitState(state: ScreenState<T>)
    fun updateState(state: ScreenState<T>, update: (oldContent: T, newContent: T) -> T)
    fun <S> pagedStateChanged(
        pagedState: PagedState<S>,
        emitEmptyAsContent: Boolean = false,
        content: (items: List<S>) -> T,
    )
    fun <S, R> statesChanged(
        pagedState: PagedState<S>,
        resultState: ResultState<R>,
        isEmpty: (content: T) -> Boolean,
        content: (items: List<S>, data: R, paging: PagingUiModel) -> T,
    )
}

class ScreenStateHandlerImpl<T : Any>(
    defaultState: ScreenState<T> = ScreenState.Progress,
) : ScreenStateHandler<T> {

    private val _screenState = MutableStateFlow(defaultState)
    override val screenState: StateFlow<ScreenState<T>> = _screenState
    override val contentState: T?
        get() = (screenState.value as? ScreenState.Content)?.data

    override fun emitProgress() {
        _screenState.value = ScreenState.Progress
    }

    override fun emitContent(state: T) {
        _screenState.value = ScreenState.Content(state)
    }

    override fun emitEmpty(customMessage: TextRes?) {
        _screenState.value = ScreenState.Empty(customMessage)
    }

    override fun emitError(error: AppError, customMessage: TextRes?) {
        _screenState.value = error.toScreenState(customMessage)
    }

    override fun emitOffline() {
        _screenState.value = ScreenState.Offline
    }

    override fun updateContent(update: (T) -> T) {
        _screenState.update {
            if (it is ScreenState.Content) {
                ScreenState.Content(update(it.data))
            } else {
                it
            }
        }
    }

    override fun emitState(state: ScreenState<T>) {
        _screenState.value = state
    }

    override fun updateState(state: ScreenState<T>, update: (oldData: T, newData: T) -> T) {
        _screenState.update {
            val currentData = (it as? ScreenState.Content)?.data
            if (currentData != null && state is ScreenState.Content) {
                ScreenState.Content(update(currentData, state.data))
            } else {
                state
            }
        }
    }

    override fun <S> pagedStateChanged(
        pagedState: PagedState<S>,
        emitEmptyAsContent: Boolean,
        content: (items: List<S>) -> T,
    ) {
        _screenState.value = getScreenStateForPagedSate(pagedState, emitEmptyAsContent, content)
    }

    @SuppressWarnings("CyclomaticComplexMethod")
    override fun <S, R> statesChanged(
        pagedState: PagedState<S>,
        resultState: ResultState<R>,
        isEmpty: (content: T) -> Boolean,
        content: (items: List<S>, data: R, paging: PagingUiModel) -> T,
    ) {
        val anyError = resultState is ResultState.Error || (pagedState is PagedState.Error && pagedState.firstPage)
        val anyProgress = resultState is ResultState.Progress || (pagedState is PagedState.Progress && pagedState.firstPage && pagedState.refreshing.not())
        val anyRefreshing = resultState.isRefreshing() || pagedState.isRefreshing()

        when {
            anyError -> {
                emitError(
                    (resultState as? ResultState.Error)?.error ?: (pagedState as? PagedState.Error)?.error ?: AppError.UnknownError,
                )
            }
            anyProgress && anyRefreshing.not() -> {
                emitProgress()
            }
            // This should not happen, it's just safe check so we do not have to cast in the els branch.
            resultState !is ResultState.Content -> {
                emitProgress()
            }
            else -> {
                val contentValue = content(
                    pagedState.data,
                    resultState.value,
                    pagedState.toUiModel().copy(refreshing = anyRefreshing),
                )
                val isEmptyValue = isEmpty(contentValue)

                if (isEmptyValue) {
                    emitEmpty()
                } else {
                    emitContent(contentValue)
                }
            }
        }
    }

    private fun <S> getScreenStateForPagedSate(
        pagedState: PagedState<S>,
        emitEmptyAsContent: Boolean,
        content: (items: List<S>) -> T,
    ): ScreenState<T> =
        pagedState.toScreenState(emitEmptyAsContent, content)
}
