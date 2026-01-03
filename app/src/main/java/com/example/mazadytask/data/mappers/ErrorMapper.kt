package com.example.mazadytask.data.mappers

import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.exception.ApolloNetworkException
import com.example.mazadytask.domain.model.AppError

// Maps low-level exceptions to domain-level [AppError]
fun Throwable.toAppError(): AppError = when (this) {
    // no internet, DNS error, timeout, connection refused, airplane mode, Socket timeout before response.
    is ApolloNetworkException -> AppError.Network(message)
    // HTTP error (404, 500, etc.)
    is ApolloHttpException -> AppError.Server(
        code = statusCode,
        message = message
    )

    else -> AppError.Unknown(this)
}