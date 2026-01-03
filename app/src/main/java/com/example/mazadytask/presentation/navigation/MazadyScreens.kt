package com.example.mazadytask.presentation.navigation

import com.example.mazadytask.presentation.base.Screens
import kotlinx.serialization.Serializable


sealed interface MazadyScreens {
    @Serializable
    data object LaunchList : Screens
    @Serializable
    data class LaunchDetails(val launchId: String) : Screens
}
