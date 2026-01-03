package com.example.mazadytask.presentation.utils

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

/**
 * Composable function that returns a localized error message.
 * Uses string resources as fallback when server message is null/blank.
 */
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
