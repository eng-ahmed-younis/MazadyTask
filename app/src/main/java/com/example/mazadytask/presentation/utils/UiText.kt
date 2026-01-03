package com.example.mazadytask.presentation.utils

import android.content.Context
import androidx.annotation.StringRes


sealed class UiText {
    data class Dynamic(val value: String) : UiText()
    data class Resource(@StringRes val resId: Int) : UiText()
}

fun UiText.asString(context: Context): String {
    return when (this) {
        is UiText.Dynamic -> value
        is UiText.Resource -> context.getString(resId)
    }
}
