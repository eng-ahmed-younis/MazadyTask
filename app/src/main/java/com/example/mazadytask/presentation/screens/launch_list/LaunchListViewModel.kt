package com.example.mazadytask.presentation.screens.launch_list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mazadytask.di.dispatcher.DispatchersProvider
import com.example.mazadytask.domain.model.LaunchListItem
import com.example.mazadytask.domain.usecase.GetLaunchesPagingUseCase
import com.example.mazadytask.presentation.base.mvi.MviBaseViewModel
import com.example.mazadytask.presentation.base.mvi.MviEffect
import com.example.mazadytask.presentation.navigation.MazadyScreens
import com.example.mazadytask.presentation.screens.launch_list.mvi.LaunchListAction
import com.example.mazadytask.presentation.screens.launch_list.mvi.LaunchListIntent
import com.example.mazadytask.presentation.screens.launch_list.mvi.LaunchListReducer
import com.example.mazadytask.presentation.screens.launch_list.mvi.LaunchListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject


@HiltViewModel
class LaunchListViewModel @Inject constructor(
    private val getLaunchesUseCase: GetLaunchesPagingUseCase,
    private val dispatchers: DispatchersProvider
) : MviBaseViewModel<LaunchListState, LaunchListAction, LaunchListIntent>(
    initialState = LaunchListState(),
    reducer = LaunchListReducer()
) {

    val launchesPagingFlow: Flow<PagingData<LaunchListItem>> by lazy {
        println("🔥 LAZY BLOCK EXECUTING NOW!")  // ✅ This fires in step 4
        onAction(LaunchListAction.OnLoading(true))

        val flow = getLaunchesUseCase(pageSize = 20)
            .catch { error ->
                onAction(LaunchListAction.OnLoading(false))
                onEffect(
                    MviEffect.OnErrorDialog(
                        error = error.message ?: "Failed to load launches"
                    )
                )
            }
            .cachedIn(viewModelScope)

        onAction(LaunchListAction.OnLoading(false))
        println("🔥 LAZY BLOCK FINISHED!")
        flow
    }


    override fun handleIntent(intent: LaunchListIntent) {

        when (intent) {
            is LaunchListIntent.LoadLaunches -> launchesPagingFlow
            is LaunchListIntent.OnLaunchClicked -> onEffect(
                MviEffect.Navigate(
                    MazadyScreens.LaunchDetails(intent.launchId)
                )
            )

            else -> {}
        }


    }




}

