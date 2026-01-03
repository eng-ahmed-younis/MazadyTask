package com.example.mazadytask.presentation.screens.launch_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mazadytask.presentation.base.mvi.MviEffect

class LaunchDetailScreen {
}

/*
@Composable
fun LaunchDetailScreenRoute(
    onNavigateBack: () -> Unit
) {
    val viewModel: LaunchDetailViewModel = hiltViewModel()
    val state = viewModel.viewState
    val onIntent = viewModel::onIntent

    // Error dialog state
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isErrorSheetVisible by remember { mutableStateOf(false) }

    // Effect handling
    LaunchedEffect(Unit) {
        viewModel.effects.onEach { effect ->
            when (effect) {
                is MviEffect.Back -> onNavigateBack()
                is MviEffect.OnErrorDialog -> {
                    errorMessage = effect.error
                    isErrorSheetVisible = true
                }
            }
        }.launchIn(scope)
    }
}*/
