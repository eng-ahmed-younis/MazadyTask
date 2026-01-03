package com.example.mazadytask.presentation.screens.launch_details.mvi

import com.example.mazadytask.presentation.base.mvi.MviAction


sealed interface LaunchDetailsAction : MviAction {
    data class SetLoading(val value: Boolean) : LaunchDetailsAction
    data class SetLaunchId(val id: String) : LaunchDetailsAction
    data class SetError(val message: String?) : LaunchDetailsAction

    data class SetDetails(
        val missionName: String,
        val rocketName: String,
        val launchDateUtc: String,
        val success: Boolean?
    ) : LaunchDetailsAction
}
