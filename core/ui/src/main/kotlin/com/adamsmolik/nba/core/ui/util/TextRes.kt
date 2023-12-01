package com.adamsmolik.nba.core.ui.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource

// All variants need to implement equals and hashCode, so Compose can check if the actual text has changed.
sealed class TextRes {
    abstract val resId: Int

    data class Regular(
        override val resId: Int,
    ) : TextRes() {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Regular) return false

            if (resId != other.resId) return false

            return true
        }

        override fun hashCode(): Int {
            var result = resId
            result *= 31
            return result
        }
    }

    class Formatted(
        override val resId: Int,
        vararg values: Any,
    ) : TextRes() {
        val args = values

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Formatted) return false

            if (resId != other.resId) return false
            if (!args.contentEquals(other.args)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = resId
            result = 31 * result + args.contentHashCode()
            return result
        }
    }

    class Quantity(
        override val resId: Int,
        val quantity: Int,
        vararg values: Any,
    ) : TextRes() {
        val args = values

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Quantity) return false

            if (resId != other.resId) return false
            if (quantity != other.quantity) return false
            if (!args.contentEquals(other.args)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = resId
            result = 31 * result + quantity
            result = 31 * result + args.contentHashCode()
            return result
        }
    }
}

@SuppressWarnings("SpreadOperator")
fun TextRes.asString(context: Context) = when (this) {
    is TextRes.Regular -> context.getString(this.resId)
    is TextRes.Formatted -> context.getString(this.resId, *this.args)
    is TextRes.Quantity -> context.resources.getQuantityString(
        this.resId,
        this.quantity,
        *this.args,
    )
}

@SuppressWarnings("SpreadOperator")
@Composable
fun TextRes.asString() = when (this) {
    is TextRes.Regular -> stringResource(this.resId)
    is TextRes.Formatted -> stringResource(this.resId, *this.args)
    is TextRes.Quantity -> pluralStringResource(this.resId, this.quantity, *this.args)
}
