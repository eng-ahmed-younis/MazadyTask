package com.example.mazadytask.presentation.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.mazadytask.domain.model.LaunchListItem
import com.example.mazadytask.presentation.screens.launch_list.mvi.LaunchListIntent
import com.example.mazadytask.presentation.ui.theme.LocalLaunchColors
import com.example.mazadytask.presentation.ui.theme.MazadyAppTheme
import com.example.mazadytask.presentation.utils.spacing.AppSpacing
import kotlinx.coroutines.flow.flowOf

@Composable
fun LaunchList(
    pagingItems: LazyPagingItems<LaunchListItem>,
    onIntent: (LaunchListIntent) -> Unit,
    paddingValues: PaddingValues
) {
    val colors = LocalLaunchColors.current

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(AppSpacing.space_12),
        contentPadding = PaddingValues(
            start = AppSpacing.space_16,
            end = AppSpacing.space_16,
            top = paddingValues.calculateTopPadding() + AppSpacing.space_8,
            bottom = paddingValues.calculateBottomPadding() + AppSpacing.space_16
        ),
        modifier = Modifier
            .fillMaxSize()
            .background(colors.surfaceBackground)
            .navigationBarsPadding()
            .semantics { contentDescription = "Launches list" }
    ) {

        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.id }
        ) { index ->
            val launch = pagingItems[index] ?: return@items
            LaunchItem(
                launch = launch,
                onLaunchClick = {
                    onIntent(
                        LaunchListIntent.OnLaunchClicked(
                            launchId = launch.id
                        )
                    )
                }
            )
        }

        // Append loading footer
        if (pagingItems.loadState.append is LoadState.Loading) {
            item(key = "append_loader") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = AppSpacing.space_16),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LaunchListPreview() {
    val sampleLaunches = List(10) { index ->
        LaunchListItem(
            id = "id_$index",
            missionName = "Mission Apollo ${index + 1}",
            site = "site $index",
            missionPatchUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCuw1ElaVhuL1nn9lXdoJVPOLWF4muWFtIvw&s"
        )
    }

    val pagingItems = flowOf(PagingData.from(sampleLaunches)).collectAsLazyPagingItems()

    MazadyAppTheme {
        LaunchList(
            pagingItems = pagingItems,
            onIntent = {},
            paddingValues = PaddingValues(0.dp)
        )
    }
}

