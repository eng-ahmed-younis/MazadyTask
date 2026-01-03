package com.example.mazadytask.domain.model

data class Page<T>(
    val items: List<T>,
    val nextCursor: String?,  // Cursor to request the next page
    val hasMore: Boolean
)
