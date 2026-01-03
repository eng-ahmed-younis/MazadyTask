package com.example.mazadytask.presentation.screens.launch_list.mvi

import androidx.compose.runtime.Stable
import com.example.mazadytask.domain.model.AppError
import com.example.mazadytask.presentation.base.mvi.MviIntent

@Stable
sealed interface LaunchListIntent : MviIntent {
    data object LoadLaunches : LaunchListIntent


    data object ScreenOpened : LaunchListIntent
    data object Refresh : LaunchListIntent
    data object LoadNextPage : LaunchListIntent
    data class OnLaunchClicked(val launchId: String) : LaunchListIntent


    // Sent from Compose when Paging loadState has an error
    data class OnPagingError(val error: AppError) : LaunchListIntent

    // Optional: call this after user dismisses the dialog/snackbar
    data object ClearPagingError : LaunchListIntent


}
