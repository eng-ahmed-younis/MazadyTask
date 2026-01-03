package com.example.mazadytask.presentation.screens.launch_details.mvi

import com.example.mazadytask.presentation.base.mvi.MviIntent


sealed interface LaunchDetailsIntent : MviIntent {
    data class LoadDetails(val launchId: String) : LaunchDetailsIntent
    data object Retry : LaunchDetailsIntent
    data object Back : LaunchDetailsIntent
}