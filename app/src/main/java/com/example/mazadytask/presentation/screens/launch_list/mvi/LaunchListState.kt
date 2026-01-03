package com.example.mazadytask.presentation.screens.launch_list.mvi

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.example.mazadytask.domain.model.LaunchListItem
import com.example.mazadytask.presentation.base.mvi.MviState

@Immutable
data class LaunchListState(
    val isLoading: Boolean = false,
    val launchesPaging: PagingData<LaunchListItem> = PagingData.empty() ,
    val errorMessage: String? = null
) : MviState
