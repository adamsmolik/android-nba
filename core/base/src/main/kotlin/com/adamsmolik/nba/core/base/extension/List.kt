package com.adamsmolik.nba.core.base.extension

inline fun <T> List<T>.updateItem(predicate: (T) -> Boolean, update: (T) -> T): List<T> = map { if (predicate(it)) update(it) else it }
inline fun <T> List<T>.updateItem(position: Int, update: (T) -> T): List<T> = mapIndexed { index, item -> if (index == position) update(item) else item }
