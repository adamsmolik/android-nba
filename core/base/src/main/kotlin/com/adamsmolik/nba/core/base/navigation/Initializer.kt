package com.adamsmolik.nba.core.base.navigation

import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Base64
import androidx.navigation.NavType

const val ARG_INITIALIZER = "initializer"

interface Initializer : Parcelable

class InitializerNavType<T : Initializer>(private val clazz: Class<T>) : NavType<T>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): T? = bundle.getParcelable(key)

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putParcelable(key, value)
    }

    override fun parseValue(value: String): T = value.decodeToInitializer(clazz)
}

fun Initializer.encodeToString(): String {
    val parcel = Parcel.obtain().apply {
        writeParcelable(this@encodeToString, 0)
    }

    val result: String = Uri.encode(Base64.encodeToString(parcel.marshall(), Base64.DEFAULT))

    parcel.recycle()

    return result
}

fun <T : Initializer> String.decodeToInitializer(clazz: Class<T>): T {
    val bytes = Base64.decode(Uri.decode(this), Base64.DEFAULT)

    val parcel = Parcel.obtain().apply {
        unmarshall(bytes, 0, bytes.size)
        setDataPosition(0)
    }

    val result: T? = parcel.readParcelable(clazz.classLoader)

//     Do not use non-deprecated method on Android 33. There is a bug https://issuetracker.google.com/issues/240585930.
//     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//        parcel.readParcelable(clazz.classLoader, clazz)
//    } else {
//        parcel.readParcelable(clazz.classLoader)
//    }

    parcel.recycle()

    return requireNotNull(result) {
        "Not able to deserialize initializer for class=${clazz.simpleName}"
    }
}
