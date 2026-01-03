package com.example.mazadytask.presentation.screens.launch_details.mvi

import androidx.compose.runtime.Immutable
import com.example.mazadytask.presentation.base.mvi.MviState

@Immutable
data class LaunchDetailsState(
    val isLoading: Boolean = false,
    val launchId: String = "",

    // details fields directly in state
    val missionName: String = "",
    val rocketName: String = "",
    val launchDateUtc: String = "",
    val success: Boolean? = null,

    val errorMessage: String? = null
) : MviState
