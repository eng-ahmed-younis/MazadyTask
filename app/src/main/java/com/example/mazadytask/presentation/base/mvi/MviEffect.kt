package com.example.mazadytask.presentation.base.mvi

import com.example.mazadytask.presentation.base.Screens

// one time actions that are not related to the state
interface MviEffect {

    data class Navigate(val screen: Screens) : MviEffect

    data class OnErrorDialog(val error: String) : MviEffect

    data class OnSuccessDialog(val success: String) : MviEffect

}
