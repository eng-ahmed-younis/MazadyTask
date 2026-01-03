package com.example.mazadytask.presentation.navigation

import androidx.navigation.NavController
import com.example.mazadytask.presentation.base.Screens


/**
 * Simple navigation extension:
 * - Screens.Back → popBackStack()
 * - Any other Screens → delegate to your existing navigate(screen)
 */
fun NavController.navigateTo(screen: Screens) {

    when (screen) {
        is Screens.Back -> {
            popBackStack()
        }

        else -> {
            // This uses your existing type-safe navigate(Screens) extension
            navigate(screen)
        }
    }
}
