package com.example.mazadytask.presentation.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.mazadytask.R
import com.example.mazadytask.data.paging.AppErrorThrowable
import com.example.mazadytask.domain.model.AppError

sealed interface UiErrorType {
    data class Network(val message: String? ) : UiErrorType
    data class Server(val message: String?) : UiErrorType
    data class Unknown(val message: String?) : UiErrorType
}

/**
 * Converts Throwable to UiErrorType.
 * Preserves server messages but keeps them nullable for fallback handling.
 */
fun Throwable?.toUiErrorType(): UiErrorType? {
    if (this == null) return null

    val appError = (this as? AppErrorThrowable)?.appError

    return when (appError) {
        is AppError.Network -> UiErrorType.Network(message = appError.message)
        is AppError.Server -> UiErrorType.Server(message = appError.message)
        is AppError.Unknown -> UiErrorType.Unknown(message = appError.throwable.message)
        null -> UiErrorType.Unknown(message = this.message)
    }
}


// return error type and message
@Composable
fun Throwable?.toLocalizedErrorTitleAndMessage(): Pair<String, String> {
    return when (val errorType = this.toUiErrorType()) {

        is UiErrorType.Network -> {
            stringResource(R.string.network_error_type) to
                    (errorType.message?.takeIf { it.isNotBlank() }
                        ?: stringResource(R.string.error_network))
        }

        is UiErrorType.Server -> {
            stringResource(R.string.server_error_type) to
                    (errorType.message?.takeIf { it.isNotBlank() }
                        ?: stringResource(R.string.error_server))
        }

        is UiErrorType.Unknown -> {
            stringResource(R.string.unknown_error_type) to
                    (errorType.message?.takeIf { it.isNotBlank() }
                        ?: stringResource(R.string.error_unknown))
        }
        null -> {
            stringResource(R.string.unknown_error_type) to
                    stringResource(R.string.error_unknown)
        }
    }
}


fun UiErrorType.toErrorTitle(
    context: Context
): String {
    return when(this){
        is UiErrorType.Network -> context.getString(R.string.network_error_type)
        is UiErrorType.Server -> context.getString(R.string.server_error_type)
        is UiErrorType.Unknown -> context.getString(R.string.unknown_error_type)
    }
}