package com.adamsmolik.nba.core.base.error

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.adamsmolik.nba.core.base.composable.noLocalProviderFor
import com.adamsmolik.nba.core.base.util.TextRes
import javax.inject.Inject

@Immutable
data class ErrorUiModel(
    val message: TextRes,
)

// For module specific error
// interface ModuleErrorResolver {
//    fun getErrorMessageRes(error: AppError): Int?
//    fun getErrorMessage(error: AppError): TextRes? = null
// }

class ErrorHandler @Inject constructor(
//    private val resolvers: Set<@JvmSuppressWildcards ModuleErrorResolver>,
    private val errorResolver: ErrorResolver,
) {
    fun getError(error: AppError): ErrorUiModel? {
//        val errorRes = resolvers.firstNotNullOfOrNull { it.getErrorMessageRes(error) } ?: errorResolver.getErrorMessageRes(error)
        val errorRes = errorResolver.getErrorMessageRes(error)

        return if (errorRes != null) {
            ErrorUiModel(
                message = TextRes.Regular(errorRes),
            )
        } else {
            null
        }
    }
}

val LocalErrorHandler: ProvidableCompositionLocal<ErrorHandler> = staticCompositionLocalOf {
    noLocalProviderFor("LocalErrorHandler")
}
