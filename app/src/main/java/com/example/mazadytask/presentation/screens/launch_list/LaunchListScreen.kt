package com.example.mazadytask.presentation.screens.launch_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mazadytask.R
import com.example.mazadytask.domain.model.LaunchListItem
import com.example.mazadytask.presentation.base.Screens
import com.example.mazadytask.presentation.base.mvi.MviEffect
import com.example.mazadytask.presentation.screens.composables.ErrorDialog
import com.example.mazadytask.presentation.screens.composables.LaunchList
import com.example.mazadytask.presentation.screens.composables.LoadingDialog
import com.example.mazadytask.presentation.screens.composables.paging.EmptyState
import com.example.mazadytask.presentation.screens.launch_list.mvi.LaunchListIntent
import com.example.mazadytask.presentation.ui.theme.LocalLaunchColors
import com.example.mazadytask.presentation.ui.theme.MazadyAppTheme
import com.example.mazadytask.presentation.utils.PagingErrorDialogHandler
import kotlinx.coroutines.flow.flowOf

@Composable
fun LaunchListScreenRoute(
    onNavigate: (Screens) -> Unit = {}
) {
    val viewModel: LaunchListViewModel = hiltViewModel()
    val state = viewModel.viewState
    val pagingItems = viewModel.launchesPagingFlow.collectAsLazyPagingItems()
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }
    var isErrorDialogVisible by rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effects.collect { effect ->
                when (effect) {
                    is MviEffect.Navigate -> {
                        onNavigate(effect.screen)
                    }
                    is MviEffect.OnErrorDialog -> {
                        errorMessage = effect.error
                        isErrorDialogVisible = true
                    }
                }
            }
        }
    }

    PagingErrorDialogHandler(
        items = pagingItems,
        onRetry = { pagingItems.retry() }
    )

    ErrorDialog(
        visible = isErrorDialogVisible,
        message = errorMessage.orEmpty(),
        onDismiss = { isErrorDialogVisible = false }
    )

    LoadingDialog(visible = state.isLoading)

    LaunchListScreen(
        pagingItems = pagingItems,
        onIntent = viewModel::onIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchListScreen(
    pagingItems: LazyPagingItems<LaunchListItem>,
    onIntent: (LaunchListIntent) -> Unit
) {
    val colors = LocalLaunchColors.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )


    LaunchedEffect(Unit) {
        onIntent(LaunchListIntent.LoadLaunches)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.screen_title_launches),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.topBarBackground,
                    scrolledContainerColor = colors.topBarBackground,
                    titleContentColor = colors.primaryText
                ),
                scrollBehavior = scrollBehavior
            )
        },
        containerColor = colors.surfaceBackground
    ) { paddingValues ->
        when {
            // Initial loading state (first page loading, no items yet)
            pagingItems.loadState.refresh is LoadState.Loading && pagingItems.itemCount == 0  -> {
                LoadingDialog(visible = true)
            }

            // Empty state (loaded successfully but no items)
            pagingItems.itemCount == 0 -> {
                EmptyState(paddingValues = paddingValues)
            }

            // Success state (has items)
            else -> {
                LaunchList(
                    pagingItems = pagingItems,
                    onIntent = onIntent,
                    paddingValues = paddingValues
                )
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LaunchListScreenPreview(){
    val sampleLaunches = List(10) { index ->
        LaunchListItem(
            id = "id_$index",
            missionName = "Mission Apollo ${index + 1}",
            site = "site $index",
            missionPatchUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCuw1ElaVhuL1nn9lXdoJVPOLWF4muWFtIvw&s",
            isBooked = true
        )
    }

    val pagingItems = flowOf(PagingData.from(sampleLaunches)).collectAsLazyPagingItems()
    MazadyAppTheme{
        LaunchListScreen(
            pagingItems = pagingItems,
            onIntent = {}
        )
    }
}