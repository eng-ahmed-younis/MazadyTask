package com.example.mazadytask.presentation.screens.launch_details.mvi

import androidx.compose.runtime.Immutable
import com.example.mazadytask.domain.model.LaunchDetails
import com.example.mazadytask.presentation.base.mvi.MviState
import com.example.mazadytask.presentation.utils.UiErrorType

@Immutable
data class LaunchDetailsState(
    val isLoading: Boolean = false,
    val details: LaunchDetails? = null,
    val errorType: UiErrorType? = null,
    val errorMessage: String? = null
) : MviState