package com.example.mazadytask.domain.utils

import com.example.mazadytask.domain.model.AppError
import com.example.mazadytask.domain.model.AppResult


fun <T> AppResult<T>.getOrNull(): T?  =
    (this as? AppResult.Success)?.data

fun <T> AppResult<T>.errorOrNull(): AppError? =
    (this as? AppResult.Error)?.error