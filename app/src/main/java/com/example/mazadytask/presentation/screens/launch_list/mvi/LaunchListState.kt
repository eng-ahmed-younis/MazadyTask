package com.example.mazadytask.presentation.screens.launch_list.mvi

import androidx.compose.runtime.Immutable
import com.example.mazadytask.presentation.base.mvi.MviState
import com.example.mazadytask.presentation.utils.observer.ConnectivityObserver

@Immutable
data class LaunchListState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val networkState: ConnectivityObserver.State = ConnectivityObserver.State.UnAvailable
) : MviState
