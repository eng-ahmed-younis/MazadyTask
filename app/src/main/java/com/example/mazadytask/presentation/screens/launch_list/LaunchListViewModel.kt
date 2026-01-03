package com.example.mazadytask.presentation.screens.launch_list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mazadytask.R
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
import com.example.mazadytask.presentation.utils.UiErrorType
import com.example.mazadytask.presentation.utils.UiText
import com.example.mazadytask.presentation.utils.observer.ConnectivityObserver
import com.example.mazadytask.presentation.utils.observer.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class LaunchListViewModel @Inject constructor(
    private val getLaunchesUseCase: GetLaunchesPagingUseCase,
    connectivityObserver: ConnectivityObserver,
    private val dispatchers: DispatchersProvider
) : MviBaseViewModel<LaunchListState, LaunchListAction, LaunchListIntent>(
    initialState = LaunchListState(),
    reducer = LaunchListReducer()
) {

    init {
        observeNetwork(connectivityObserver)
    }

    val launchesPagingFlow: Flow<PagingData<LaunchListItem>> by lazy {
        onAction(LaunchListAction.OnLoading(true))

        val flow = getLaunchesUseCase(pageSize = 20)
            .flowOn(dispatchers.io)
            .catch { error ->
                onAction(LaunchListAction.OnLoading(false))
                onEffect(
                    MviEffect.OnErrorDialog(
                        errorMessage = UiText.Dynamic(error.message ?: "Failed to load launches")
                    )
                )
            }
            .cachedIn(viewModelScope)
            .also { onAction(LaunchListAction.OnLoading(false)) }
        flow
    }


    override fun handleIntent(intent: LaunchListIntent) {

        when (intent) {
            is LaunchListIntent.OnLaunchClicked -> onEffect(
                MviEffect.Navigate(
                    MazadyScreens.LaunchDetails(intent.launchId)
                )
            )
        }
    }


    private fun observeNetwork(
        observer: ConnectivityObserver
    ) {
        NetworkStatus.observeAsState(
            connectivityObserver = observer,
            scope = viewModelScope
        ).onEach { state ->
            onAction(
                LaunchListAction.OnNetworkStateChanged(state)
            )
            showNoInternetConnectionDialog(networkState = state)
        }.launchIn(viewModelScope)
    }


    private fun showNoInternetConnectionDialog(
        networkState: ConnectivityObserver.State
    ) {
        when (networkState) {
            ConnectivityObserver.State.UnAvailable,
            ConnectivityObserver.State.Lost -> {
                onEffect(
                    MviEffect.OnErrorDialog(
                        errorType = UiErrorType.Network(null),
                        errorMessage = UiText.Resource(resId = R.string.no_internet_connection)
                    )
                )
            }

            else -> {}
        }
    }


}

