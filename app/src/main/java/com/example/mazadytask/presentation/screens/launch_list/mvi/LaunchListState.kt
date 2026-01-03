package com.example.mazadytask.presentation.screens.launch_list.mvi

import androidx.compose.runtime.Immutable
import com.example.mazadytask.presentation.base.mvi.MviState

@Immutable
data class LaunchListState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) : MviState
