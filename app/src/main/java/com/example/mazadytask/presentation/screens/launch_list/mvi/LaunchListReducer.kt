package com.example.mazadytask.presentation.screens.launch_list.mvi

import com.example.mazadytask.presentation.base.mvi.Reducer

class LaunchListReducer : Reducer<LaunchListAction, LaunchListState> {

    override fun reduce(
        action: LaunchListAction,
        state: LaunchListState
    ): LaunchListState {
        return when(action){
            is LaunchListAction.OnLoading -> {
                state.copy(
                    isLoading = action.isLoading
                )
            }


            else -> state
        }
    }


}