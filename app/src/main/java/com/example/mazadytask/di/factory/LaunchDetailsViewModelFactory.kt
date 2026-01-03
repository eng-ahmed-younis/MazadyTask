package com.example.mazadytask.di.factory

import com.example.mazadytask.presentation.screens.launch_details.LaunchDetailsViewModel
import dagger.assisted.AssistedFactory
import kotlinx.serialization.Serializable


@Serializable
data class LaunchDetailsParams(
    val launchId: String
)

@AssistedFactory
interface LaunchDetailsViewModelFactory {
    fun create(param: LaunchDetailsParams): LaunchDetailsViewModel
}