package com.example.mazadytask.presentation.base

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

// Defines common navigation actions can be handled directly in the navigateTo()

@Stable
interface Screens {


    @Serializable
    data class Back(
        val payload: Map<String, String>? = null
    ) : Screens

}