package com.example.mazadytask.presentation.navigation

import androidx.navigation.NavController
import com.example.mazadytask.presentation.base.Screens

fun NavController.navigateTo(screen: Screens) {

    when (screen) {
        is Screens.Back -> {
            popBackStack()
        }

        else -> {
            navigate(screen)
        }
    }
}
