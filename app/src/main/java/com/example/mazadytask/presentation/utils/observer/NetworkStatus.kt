package com.example.mazadytask.presentation.utils.observer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

object NetworkStatus {

    fun observeAsState(
        connectivityObserver: ConnectivityObserver,
        scope: CoroutineScope
    ): StateFlow<ConnectivityObserver.State> {
        return connectivityObserver.observe()
            .stateIn(
                scope = scope,
                // sharing data when the first collector starts collecting and stops after 5 seconds of inactivity
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ConnectivityObserver.State.UnAvailable
            )
    }
}
