package com.adamsmolik.nba.base.paging

import com.adamsmolik.nba.base.CoreConfig
import com.adamsmolik.nba.base.arch.Result
import com.adamsmolik.nba.base.error.AppError
import com.adamsmolik.nba.base.extension.updateItem
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber

class PagedDataProvider<T>(
    private val coroutineScope: CoroutineScope,
    private val pageSize: Int = CoreConfig.Paging.PAGE_SIZE,
    private val prefetchDistance: Int = CoreConfig.Paging.PREFETCH_OFFSET,
    val loadData: suspend (offset: Int, pageSize: Int) -> Result<List<T>>,
) {
    private val mutex = Mutex()

    private val onDistanceToEndChanged = { distance: Int -> distanceToEndChanged(distance) }
    private val onRefresh = { loadFirstPage(refresh = true) }

    private val _state = MutableStateFlow<PagedState<T>>(
        progressState(
            data = emptyList(),
            firstPage = true,
            refreshing = false,
        ),
    )

    private var lastPageFetched = false
    private var initialCall: CoroutineContext? = null
    private var nextPageCall: CoroutineContext? = null

    val state: StateFlow<PagedState<T>> = _state

    fun reloadData(clearData: Boolean = false) {
        nextPageCall?.cancel()
        nextPageCall = null
        initialCall?.cancel()

        if (clearData.not() && _state.value.data.size > pageSize) {
            refreshAllData()
        } else {
            loadFirstPage(clearData)
        }
    }

    fun updateItem(predicate: (T) -> Boolean, update: (T) -> T) {
        _state.update {
            it.copyWithData(it.data.updateItem(predicate, update))
        }
    }

    fun updateItems(update: (T) -> T) {
        _state.update {
            it.copyWithData(it.data.map { update(it) })
        }
    }

    fun updateItemsBatch(update: (List<T>) -> List<T>) {
        _state.update {
            it.copyWithData(update(it.data))
        }
    }

    fun updateItemsIndexed(update: (index: Int, item: T) -> T) {
        _state.update {
            it.copyWithData(it.data.mapIndexed { index, item -> update(index, item) })
        }
    }

    fun addItem(item: T, position: Int? = null) {
        _state.update {
            it.copyWithData(
                _state.value.data.toMutableList().apply {
                    add(position ?: size, item)
                },
            )
        }
    }

    fun removeItem(predicate: (T) -> Boolean) {
        _state.update {
            it.copyWithData(it.data.filter { predicate(it).not() })
        }
    }

    fun cancel() {
        nextPageCall?.cancel()
        nextPageCall = null
        initialCall?.cancel()
        initialCall = null

        _state.value = progressState(
            data = emptyList(),
            firstPage = true,
            refreshing = false,
        )
    }

    private fun loadFirstPage(clearData: Boolean = false, refresh: Boolean = false) {
        initialCall = coroutineScope.launch {
            mutex.withLock {
                _state.value = progressState(
                    firstPage = true,
                    data = if (clearData) emptyList() else _state.value.data,
                    refreshing = refresh || (clearData.not() && _state.value.data.isNotEmpty()),
                )

                Timber.d("loadFirstPage")

                loadData(CoreConfig.Paging.INITIAL_OFFSET, pageSize)
                    .onData { data ->
                        Timber.d("Initial page loaded")
                        lastPageFetched = (data.size < pageSize).also {
                            if (it) {
                                Timber.d(
                                    "Last page reached. Page size: $pageSize. Items count ${data.size}",
                                )
                            }
                        }

                        _state.value = successState(
                            data = data,
                            firstPage = true,
                        )

                        initialCall = null
                    }
                    .onError { error ->
                        if (error is AppError.Canceled) return@onError

                        _state.value = errorState(
                            error = error,
                            firstPage = true,
                        )

                        initialCall = null
                    }
            }
        }
    }

    private fun refreshAllData() {
        initialCall = coroutineScope.launch {
            mutex.withLock {
                _state.value = progressState(
                    firstPage = true,
                    data = _state.value.data,
                    refreshing = _state.value.data.isNotEmpty(),
                )

                Timber.d("refreshAllData")
                lastPageFetched = false

                val result = mutableListOf<T>()
                val finalCount = _state.value.data.size

                while (result.size < finalCount && lastPageFetched.not()) {
                    loadData(result.size, pageSize)
                        .onData { data ->
                            result.addAll(data)
                            lastPageFetched = (data.size < pageSize).also {
                                if (it) {
                                    Timber.d("Last page reached. Page size: $pageSize. Items count ${data.size}")
                                }
                            }
                        }
                        .onError { error ->
                            if (error is AppError.Canceled) return@launch

                            _state.value = errorState(
                                error = error,
                                firstPage = true,
                            )

                            initialCall = null
                            return@launch
                        }
                }

                _state.value = successState(
                    data = result,
                    firstPage = true,
                )

                initialCall = null
            }
        }
    }

    @SuppressWarnings("ComplexCondition")
    private fun distanceToEndChanged(distanceToEnd: Int) {
        Timber.d("distanceToEndChanged, distanceToEnd: $distanceToEnd, prefetchDistance: $prefetchDistance")
        if (distanceToEnd != -1 && distanceToEnd <= prefetchDistance && !lastPageFetched && nextPageCall == null) {
            coroutineScope.launch { loadNextPage() }
        }
    }

    private suspend fun loadNextPage() {
        if (nextPageCall != null || lastPageFetched) return

        nextPageCall = coroutineContext

        mutex.withLock {
            Timber.d("loadNextPage offset:${_state.value.data.size} pageSize: $pageSize")

            _state.value = progressState(
                data = _state.value.data,
                firstPage = false,
                refreshing = false,
            )

            loadData(_state.value.data.size, pageSize)
                .onData { data ->
                    Timber.d("Next page loaded. Page size: $pageSize. Items count ${data.size}")

                    lastPageFetched = (data.size < pageSize).also {
                        if (it) {
                            Timber.d(
                                "Last page reached. Page size: $pageSize. Items count ${data.size}",
                            )
                        }
                    }

                    _state.value = successState(
                        data = _state.value.data + data,
                        firstPage = false,
                    )

                    nextPageCall = null
                }
                .onError { error ->
                    if (error is AppError.Canceled) return@onError

                    _state.value = errorState(
                        error = error,
                        firstPage = false,
                    )

                    nextPageCall = null
                }
        }
    }

    private fun <T> progressState(data: List<T>, firstPage: Boolean, refreshing: Boolean) =
        PagedState.Progress(
            data = data,
            firstPage = firstPage,
            refreshing = refreshing,
            onDistanceToEndChanged = onDistanceToEndChanged,
            onRefresh = onRefresh,
        )

    private fun <T> successState(data: List<T>, firstPage: Boolean) =
        PagedState.Success(
            data = data,
            firstPage = firstPage,
            onDistanceToEndChanged = onDistanceToEndChanged,
            onRefresh = onRefresh,
        )

    private fun errorState(error: AppError, firstPage: Boolean) = PagedState.Error(
        error = error,
        data = _state.value.data,
        firstPage = firstPage,
        onDistanceToEndChanged = onDistanceToEndChanged,
        onRefresh = onRefresh,
    )
}
