package com.example.mazadytask.data.paging


import com.example.mazadytask.domain.model.AppError
import com.example.mazadytask.domain.model.userMessage

class AppErrorThrowable(val appError: AppError) : RuntimeException(appError.userMessage())
