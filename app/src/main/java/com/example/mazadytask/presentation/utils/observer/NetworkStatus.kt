package com.example.mazadytask.presentation.utils.observer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

object NetworkStatus {

    fun observeAsState(
        connectivityObserver: ConnectivityObserver,
        scope: CoroutineScope
    ): SharedFlow<ConnectivityObserver.State> {
        return connectivityObserver.observe()
            .shareIn(
                scope = scope,
                // sharing data when the first collector starts collecting and stops after 5 seconds of inactivity
                started = SharingStarted.WhileSubscribed(5_000),
            )
    }
}


fun ConnectivityObserver.State.isOffline(): Boolean =
    this == ConnectivityObserver.State.UnAvailable ||
            this == ConnectivityObserver.State.Lost
