package com.example.mazadytask.presentation.screens.launch_details

import androidx.lifecycle.viewModelScope
import com.example.mazadytask.di.dispatcher.DispatchersProvider
import com.example.mazadytask.di.factory.LaunchDetailsParams
import com.example.mazadytask.di.factory.LaunchDetailsViewModelFactory
import com.example.mazadytask.domain.model.AppError
import com.example.mazadytask.domain.model.AppResult
import com.example.mazadytask.domain.usecase.GetLaunchDetailsUseCase
import com.example.mazadytask.presentation.base.Screens
import com.example.mazadytask.presentation.base.mvi.MviBaseViewModel
import com.example.mazadytask.presentation.base.mvi.MviEffect
import com.example.mazadytask.presentation.screens.launch_details.mvi.LaunchDetailsAction
import com.example.mazadytask.presentation.screens.launch_details.mvi.LaunchDetailsIntent
import com.example.mazadytask.presentation.screens.launch_details.mvi.LaunchDetailsReducer
import com.example.mazadytask.presentation.screens.launch_details.mvi.LaunchDetailsState
import com.example.mazadytask.presentation.utils.UiErrorType
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

@HiltViewModel(assistedFactory = LaunchDetailsViewModelFactory::class)
class LaunchDetailsViewModel @AssistedInject constructor(
    private val getLaunchDetailsUseCase: GetLaunchDetailsUseCase,
    private val dispatchers: DispatchersProvider,
    @Assisted private val params: LaunchDetailsParams
) : MviBaseViewModel<LaunchDetailsState, LaunchDetailsAction, LaunchDetailsIntent>(
    initialState = LaunchDetailsState(),
    reducer = LaunchDetailsReducer()
) {

    init {
        loadLaunchDetails()
    }

    override fun handleIntent(intent: LaunchDetailsIntent) {
        when (intent) {
            is LaunchDetailsIntent.Back -> {
                onEffect(MviEffect.Navigate(Screens.Back()))
            }
            else -> {}
        }
    }

    private fun loadLaunchDetails() {
        getLaunchDetailsUseCase(params.launchId)
            .flowOn(dispatchers.io)
            .onStart {
                onAction(LaunchDetailsAction.OnLoading(true))
            }
            .onEach {
                when (it) {
                    is AppResult.Success -> {
                        onAction(LaunchDetailsAction.OnLaunchesSuccess(it.data))
                    }

                    is AppResult.Error ->{
                        handleLaunchDetailsError(error = it)
                        onAction(LaunchDetailsAction.NoLaunchDetails(noLaunch = true))
                    }
                }
            }
            .catch { error ->
                onAction(LaunchDetailsAction.OnError(error.message ?: "Something went wrong"))
            }
            .onCompletion {
                onAction(LaunchDetailsAction.OnLoading(false))
            }.launchIn(viewModelScope)
    }


    private fun handleLaunchDetailsError(error: AppResult.Error) {
        when (error) {
            is AppError.Network -> {
                onAction(
                    LaunchDetailsAction.OnLaunchesError(
                        errorType = UiErrorType.Network(
                            message = error.message ?: "No internet connection"
                        )
                    )
                )
            }

            is AppError.Server -> {
                onAction(
                    LaunchDetailsAction.OnLaunchesError(
                        errorType = UiErrorType.Server(
                            message = error.message ?: "Server error occurred"
                        )
                    )
                )
            }
            is AppError.Unknown -> {
                onAction(
                    LaunchDetailsAction.OnLaunchesError(
                        errorType = UiErrorType.Unknown(
                            message = error.throwable.message
                        )
                    )
                )
            }
        }
    }
}