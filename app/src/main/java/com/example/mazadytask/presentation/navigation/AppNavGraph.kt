package com.example.mazadytask.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mazadytask.di.di.LaunchDetailsParams
import com.example.mazadytask.presentation.screens.launch_details.LaunchDetailsScreenRoute
import com.example.mazadytask.presentation.screens.launch_list.LaunchListScreenRoute

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MazadyScreens.LaunchList,
        modifier = modifier
    ) {

        composable<MazadyScreens.LaunchList> {
            LaunchListScreenRoute(
                onNavigate = {
                    navController.navigateTo(it)
                }
            )
        }

        composable<MazadyScreens.LaunchDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<MazadyScreens.LaunchDetails>()
            val launchDetailsScreenParams = LaunchDetailsParams(
                launchId = args.launchId,
            )

            LaunchDetailsScreenRoute(
                params = launchDetailsScreenParams,
                onNavigate = { screen ->
                    navController.navigateTo(screen)
                }
            )
        }
    }
}
