package com.example.mazadytask.presentation.base.mvi

import com.example.mazadytask.presentation.base.Screens
import com.example.mazadytask.presentation.utils.UiErrorType
import com.example.mazadytask.presentation.utils.UiText

// one time actions that are not related to the state
interface MviEffect {

    data class Navigate(val screen: Screens) : MviEffect

    data class OnErrorDialog(
        val errorType: UiErrorType = UiErrorType.Unknown(null),
        val errorMessage: UiText
    ) : MviEffect

    data class OnSuccessDialog(val success: String) : MviEffect

}
