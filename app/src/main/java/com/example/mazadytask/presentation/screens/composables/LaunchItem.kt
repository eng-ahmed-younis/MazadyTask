package com.example.mazadytask.presentation.screens.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.mazadytask.domain.model.LaunchListItem
import com.example.mazadytask.presentation.ui.theme.LocalLaunchColors
import com.example.mazadytask.presentation.ui.theme.MazadyAppTheme

@Composable
fun LaunchItem(
    modifier: Modifier = Modifier,
    launch: LaunchListItem,
    onClick: () -> Unit = {},
) {
    val colors = LocalLaunchColors.current

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = colors.cardBackground,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                           color = Color.Transparent
                        )
                )
                Log.i("LaunchItem", "missionPatchUrl: ${launch.missionPatchUrl}")
                launch.missionPatchUrl?.let {


                    val context = LocalContext.current

                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(launch.missionPatchUrl)
                            .listener(
                                onError = { _, result ->
                                    Log.e("LaunchItem", "Image load failed", result.throwable)
                                }
                            )
                            .build(),
                        contentDescription = "Mission patch",
                        modifier = Modifier.size(48.dp)
                    )

                }

            }


            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = launch.missionName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.primaryText
                )
                Text(
                    text = launch.site ?: "Unknown site",
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