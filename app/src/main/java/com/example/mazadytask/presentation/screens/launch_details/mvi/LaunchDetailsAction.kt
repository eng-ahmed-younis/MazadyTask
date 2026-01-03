package com.example.mazadytask.presentation.screens.launch_details.mvi

import com.example.mazadytask.domain.model.LaunchDetails
import com.example.mazadytask.presentation.base.mvi.MviAction
import com.example.mazadytask.presentation.utils.UiErrorType


sealed interface LaunchDetailsAction : MviAction {
    data class OnLoading(val isLoading: Boolean) : LaunchDetailsAction
    data class OnLaunchesSuccess(val details: LaunchDetails) : LaunchDetailsAction

    data class OnLaunchesError(
        val errorType: UiErrorType
    ) : LaunchDetailsAction
    data class OnError(val message: String) : LaunchDetailsAction
}