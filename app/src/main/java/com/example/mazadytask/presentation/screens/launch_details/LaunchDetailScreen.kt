package com.example.mazadytask.presentation.screens.launch_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.mazadytask.R
import com.example.mazadytask.di.factory.LaunchDetailsParams
import com.example.mazadytask.di.factory.LaunchDetailsViewModelFactory
import com.example.mazadytask.domain.model.LaunchDetails
import com.example.mazadytask.presentation.base.Screens
import com.example.mazadytask.presentation.base.mvi.MviEffect
import com.example.mazadytask.presentation.screens.composables.ErrorDialog
import com.example.mazadytask.presentation.screens.composables.LoadingDialog
import com.example.mazadytask.presentation.screens.launch_details.mvi.LaunchDetailsIntent
import com.example.mazadytask.presentation.screens.launch_details.mvi.LaunchDetailsState
import com.example.mazadytask.presentation.ui.theme.LocalLaunchColors
import com.example.mazadytask.presentation.utils.UiErrorType
import com.example.mazadytask.presentation.utils.asString
import com.example.mazadytask.presentation.utils.observer.ConnectivityObserver
import com.example.mazadytask.presentation.utils.spacing.AppSpacing
import com.example.mazadytask.presentation.utils.toErrorTitle

@Composable
fun LaunchDetailsScreenRoute(
    params: LaunchDetailsParams,
    onNavigate: (Screens) -> Unit = {}
) {
    val viewModel: LaunchDetailsViewModel =
        hiltViewModel<LaunchDetailsViewModel, LaunchDetailsViewModelFactory> { factory ->
            factory.create(param = params)
        }

    val state = viewModel.viewState
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var errorType by remember { mutableStateOf<UiErrorType?>(null) }
    var isErrorDialogVisible by remember { mutableStateOf(false) }


    LaunchedEffect(lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effects.collect { effect ->
                when (effect) {
                    is MviEffect.Navigate -> onNavigate(effect.screen)

                    is MviEffect.OnErrorDialog -> {
                        errorMessage = effect.errorMessage.asString(context = context)
                        errorType = effect.errorType
                        isErrorDialogVisible = true
                    }
                }
            }
        }
    }

    ErrorDialog(
        visible = isErrorDialogVisible,
        message = errorMessage.orEmpty(),
        title = errorType?.toErrorTitle(context),
        onDismiss = { isErrorDialogVisible = false }
    )

    LaunchDetailsScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchDetailsScreen(
    state: LaunchDetailsState,
    onIntent: (LaunchDetailsIntent) -> Unit
) {
    val colors = LocalLaunchColors.current


    LoadingDialog(visible = state.isLoading)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.details?.missionName ?: "",
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onIntent(LaunchDetailsIntent.Back)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = colors.primaryText
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.topBarBackground,
                    titleContentColor = colors.primaryText
                )
            )
        },
        containerColor = colors.surfaceBackground
    ) { paddingValues ->
        when{
            state.noLaunchDetails -> EmptyLaunch()
            else -> DetailsContent(
                state = state,
                paddingValues = paddingValues
            )
        }
    }
}

@Composable
fun EmptyLaunch(
    text: String = stringResource(R.string.no_launch_data),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(AppSpacing.space_16)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
    }
}



@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LaunchDetailsScreenPreview() {
    LaunchDetailsScreen(
        state = LaunchDetailsState(
            isLoading = false,
            details = LaunchDetails(
                id = "1",
                missionName = "Good further"
            ),
        ),
        onIntent = {}
    )
}