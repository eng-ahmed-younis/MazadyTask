package com.example.mazadytask.domain.model

// Typed error representation for the domain layer.
// This allows the UI to react differently based on the error type
// [network] vs [server] vs [unknown].
sealed class AppError {

    // Network-related error (no internet, timeout, DNS failure, etc.).

    data class Network(val message: String? = null) : AppError()

    // Server-side error (GraphQL errors, HTTP status codes, etc.).

    data class Server(
        val code: Int? = null,
        val message: String? = null
    ) : AppError()

    data class Unknown(val throwable: Throwable) : AppError()
}


fun AppError.userMessage(): String? = when (this) {
    is AppError.Network -> message
    is AppError.Server -> message
    is AppError.Unknown -> throwable.message
}
