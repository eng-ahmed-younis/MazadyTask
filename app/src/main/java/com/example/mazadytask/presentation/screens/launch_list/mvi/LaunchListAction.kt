package com.example.mazadytask.presentation.screens.launch_list.mvi

import com.example.mazadytask.domain.model.LaunchListItem
import com.example.mazadytask.presentation.base.mvi.MviAction
import androidx.paging.PagingData


sealed interface LaunchListAction : MviAction {
    data class OnLoading(val isLoading: Boolean) : LaunchListAction
    data class PagingDataLoaded(val data: PagingData<LaunchListItem>) : LaunchListAction
    data class Error(val message: String) : LaunchListAction
}
