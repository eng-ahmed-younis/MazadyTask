package com.example.mazadytask.presentation.screens.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.mazadytask.R
import com.example.mazadytask.domain.model.LaunchListItem
import com.example.mazadytask.presentation.ui.theme.LocalLaunchColors
import com.example.mazadytask.presentation.ui.theme.MazadyAppTheme
import com.example.mazadytask.presentation.utils.spacing.AppSpacing

@Composable
fun LaunchItem(
    modifier: Modifier = Modifier,
    launch: LaunchListItem,
    onLaunchClick: () -> Unit = {},
) {
    val colors = LocalLaunchColors.current
    val context = LocalContext.current

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(AppSpacing.space_72)
            .clickable(onClick = onLaunchClick),
        shape = RoundedCornerShape(AppSpacing.space_12),
        color = colors.cardBackground,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = AppSpacing.space_16),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(AppSpacing.space_48)
                    .clip(RoundedCornerShape(AppSpacing.space_12)),
                contentAlignment = Alignment.Center
            ) {

                launch.missionPatchUrl?.let { imageUrl ->
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(imageUrl)
                            .build(),
                        contentDescription = "Mission patch",
                        modifier = Modifier.size(AppSpacing.space_48)
                    )
                }
            }

            Spacer(modifier = Modifier.width(AppSpacing.space_16))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = launch.missionName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.primaryText
                )
                Spacer(modifier = Modifier.height(AppSpacing.space_4))
                Text(
                    text = launch.site ?: stringResource(R.string.unknown_site),
                    fontSize = 14.sp,
                    color = colors.secondaryText
                )
            }
        }
    }
}



@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LaunchItemPreview() {
    MazadyAppTheme {
        LaunchItem(
            launch = LaunchListItem(
                id = "1",
                missionName = "Falcon 1",
                site = "CRS-21",
                missionPatchUrl = "https://st4.depositphotos.com/8345768/23766/i/450/depositphotos_237666660-stock-photo-closeup-head-tiger-black-background.jpg",
                isBooked = false
            )
        ) {

        }
    }
}