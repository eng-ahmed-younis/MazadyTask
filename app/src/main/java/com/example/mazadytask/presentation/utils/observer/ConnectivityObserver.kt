package com.example.mazadytask.presentation.utils.observer

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe () : Flow<State>

    enum class State{
        Available , UnAvailable , Losing , Lost
    }
}