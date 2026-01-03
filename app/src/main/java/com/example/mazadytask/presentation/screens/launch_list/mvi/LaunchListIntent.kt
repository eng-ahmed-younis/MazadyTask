package com.example.mazadytask.presentation.screens.launch_list.mvi

import androidx.compose.runtime.Stable
import com.example.mazadytask.domain.model.AppError
import com.example.mazadytask.presentation.base.mvi.MviIntent

@Stable
sealed interface LaunchListIntent : MviIntent {
    data class OnLaunchClicked(val launchId: String) : LaunchListIntent
}
