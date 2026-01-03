package com.example.mazadytask.presentation.screens.launch_details.mvi

import com.example.mazadytask.presentation.base.mvi.Reducer


class LaunchDetailsReducer : Reducer<LaunchDetailsAction, LaunchDetailsState> {
    override fun reduce(action: LaunchDetailsAction, state: LaunchDetailsState): LaunchDetailsState {
        return when (action) {
            is LaunchDetailsAction.OnLoading -> state.copy(
                isLoading = action.isLoading
            )

            is LaunchDetailsAction.OnError -> {
                state.copy(
                    errorMessage = action.message
                )
            }
            is LaunchDetailsAction.OnLaunchesError -> {
                state.copy(
                    errorType = action.errorType
                )
            }
            is LaunchDetailsAction.OnLaunchesSuccess -> {
                state.copy(
                    details = action.details
                )
            }
        }
    }
}