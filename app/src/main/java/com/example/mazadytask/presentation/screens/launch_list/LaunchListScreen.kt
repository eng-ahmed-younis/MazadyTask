package com.example.mazadytask.presentation.screens.launch_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mazadytask.presentation.base.Screens
import com.example.mazadytask.presentation.base.mvi.MviEffect
import com.example.mazadytask.presentation.screens.launch_list.composables.ErrorDialog
import com.example.mazadytask.presentation.screens.launch_list.composables.LaunchItem
import com.example.mazadytask.presentation.screens.launch_list.mvi.LaunchListIntent
import com.example.mazadytask.presentation.screens.launch_list.mvi.LaunchListState
import com.example.mazadytask.presentation.ui.theme.LocalLaunchColors
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Composable
fun LaunchListScreenRoute(
    onNavigate: (Screens) -> Unit = {}
) {
    val viewModel: LaunchListViewModel = hiltViewModel()

    val state = viewModel.viewState
    val onIntent = viewModel::onIntent
    val scope = rememberCoroutineScope()

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isErrorSheetVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effects
            .onEach { effect ->
                when (effect) {
                    is MviEffect.Navigate -> {
                        onNavigate(effect.screen)
                    }

                    is MviEffect.OnErrorDialog -> {
                        errorMessage = effect.error
                        isErrorSheetVisible = true
                    }
                }
            }.launchIn(scope)
    }

    LaunchListScreen(
        state = state,
        onIntent = onIntent,
        errorMessage = errorMessage ?: "",
        isErrorSheetVisible = isErrorSheetVisible,
        hideBottomSheet = {
            isErrorSheetVisible = false
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchListScreen(
    state: LaunchListState,
    onIntent: (LaunchListIntent) -> Unit,
    errorMessage: String,
    isErrorSheetVisible: Boolean,
    hideBottomSheet: () -> Unit,
) {
    val colors = LocalLaunchColors.current

    LaunchedEffect(Unit) {
        onIntent(LaunchListIntent.LoadLaunches)
    }

    ErrorDialog(
        visible = isErrorSheetVisible,
        message = errorMessage,
        onDismiss = hideBottomSheet
    )

    // ✅ Wrap PagingData into Flow so Compose Paging can collect it
    val pagingItems = remember(state.launchesPaging) {
        kotlinx.coroutines.flow.flowOf(state.launchesPaging)
    }.collectAsLazyPagingItems()

    // Loading & errors from Paging
    val refreshState = pagingItems.loadState.refresh
    val appendState = pagingItems.loadState.append

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.surfaceBackground)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            stickyHeader {
                Text(
                    text = "Launches",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.secondaryText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors.surfaceBackground)
                        .padding(vertical = 12.dp)
                )
            }

            // ✅ Paging items
            items(
                count = pagingItems.itemCount,
                key = { index -> pagingItems[index]?.id ?: index }
            ) { index ->
                val launch = pagingItems[index] ?: return@items
                LaunchItem(
                    launch = launch,
                    onClick = {
                        onIntent(LaunchListIntent.OnLaunchClicked(launchId = launch.id))
                    }
                )
            }

            // ✅ Footer: append loading
            if (appendState is androidx.paging.LoadState.Loading) {
                item(key = "append_loader") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            // ✅ Footer: append error (retry)
            if (appendState is androidx.paging.LoadState.Error) {
                item(key = "append_error") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = appendState.error.message ?: "Error loading more",
                            color = colors.secondaryText
                        )
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { pagingItems.retry() }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }

        // ✅ Full screen refresh loading (first page)
        if (refreshState is androidx.paging.LoadState.Loading && pagingItems.itemCount == 0) {
            Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        // ✅ Full screen refresh error (first page)
        if (refreshState is androidx.paging.LoadState.Error && pagingItems.itemCount == 0) {
            Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                    Text(
                        text = refreshState.error.message ?: "Error loading launches",
                        color = colors.secondaryText
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { pagingItems.retry() }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}
