package com.example.mazadytask.presentation.utils


import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems


fun LazyPagingItems<*>.isInitialLoading(): Boolean =
    loadState.refresh is LoadState.Loading && itemCount == 0

