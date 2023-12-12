package com.adamsmolik.nba.core.base.logging

import javax.inject.Inject
import timber.log.Timber

class TimberDebugTree @Inject constructor() : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String {
        return "NBA:(${element.fileName}:${element.lineNumber})#${element.methodName}"
    }
}
