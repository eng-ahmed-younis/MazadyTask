package com.example.mazadytask.presentation.screens.launch_list

import androidx.lifecycle.viewModelScope
import com.example.mazadytask.di.dispatcher.DispatchersProvider
import com.example.mazadytask.domain.usecase.GetLaunchesPagingUseCase
import com.example.mazadytask.presentation.base.mvi.MviBaseViewModel
import com.example.mazadytask.presentation.base.mvi.MviEffect
import com.example.mazadytask.presentation.screens.launch_list.mvi.LaunchListIntent
import com.example.mazadytask.presentation.screens.launch_list.mvi.LaunchListReducer
import com.example.mazadytask.presentation.screens.launch_list.mvi.LaunchListState
import com.example.mazadytask.presentation.screens.launch_list.mvi.LaunchListAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


@HiltViewModel
class LaunchListViewModel @Inject constructor(
    private val getLaunchesUseCase: GetLaunchesPagingUseCase,
    private val dispatchers: DispatchersProvider
) : MviBaseViewModel<LaunchListState, LaunchListAction, LaunchListIntent>(
    initialState = LaunchListState(),
    reducer = LaunchListReducer()
) {

    override fun handleIntent(intent: LaunchListIntent) {

        when (intent) {
            is LaunchListIntent.LoadLaunches -> {
                loadLaunches()
            }

            else -> {

            }
        }

    }


    private fun loadLaunches(pageSize: Int = 20) {
        getLaunchesUseCase(pageSize)
            .onStart {
                onAction(LaunchListAction.OnLoading(true))
            }
            .onEach {
                onAction(LaunchListAction.PagingDataLoaded(data = it))
            }
            .catch { error ->
                onEffect(MviEffect.OnErrorDialog(error = error.message ?: ""))
            }
            .onCompletion {
                onAction(LaunchListAction.OnLoading(false))
            }
            .launchIn(viewModelScope)
    }


}