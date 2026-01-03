package com.example.mazadytask.presentation.utils

import androidx.compose.runtime.*
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.mazadytask.presentation.screens.composables.ErrorDialog


@Composable
fun <T : Any> PagingErrorDialogHandler(
    items: LazyPagingItems<T>,
    onDismiss: () -> Unit = {},
    onRetry: () -> Unit = { items.retry() }
) {
    // Get error from paging (refresh or append)
    val throwable: Throwable? = remember(items.loadState) {
        (items.loadState.refresh as? LoadState.Error)?.error
            ?: (items.loadState.append as? LoadState.Error)?.error
    }

    // Control dialog visibility
    var showDialog by remember(throwable) {
        mutableStateOf(throwable != null)
    }

    // Show error dialog when there's an error
    if (showDialog && throwable != null) {
        val title = throwable.toLocalizedErrorTitleAndMessage().first
        val message = throwable.toLocalizedErrorTitleAndMessage().second

        ErrorDialog(
            visible = true,
            title = title,
            message = message,
            onDismiss = {
                showDialog = false
                onDismiss()
            }
        )
    }
}