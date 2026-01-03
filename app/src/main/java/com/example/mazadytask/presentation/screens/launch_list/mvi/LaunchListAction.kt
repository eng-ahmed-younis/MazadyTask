package com.example.mazadytask.presentation.screens.launch_list.mvi

import com.example.mazadytask.presentation.base.mvi.MviAction
import com.example.mazadytask.presentation.screens.launch_details.mvi.LaunchDetailsAction
import com.example.mazadytask.presentation.utils.observer.ConnectivityObserver


sealed interface LaunchListAction : MviAction {
    data class OnLoading(val isLoading: Boolean) : LaunchListAction
    data class Error(val message: String) : LaunchListAction
    data class OnNetworkStateChanged(
        val state: ConnectivityObserver.State
    ) : LaunchListAction
}
