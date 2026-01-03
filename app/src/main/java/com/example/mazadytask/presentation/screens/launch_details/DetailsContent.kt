package com.example.mazadytask.presentation.screens.launch_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.mazadytask.presentation.screens.launch_details.mvi.LaunchDetailsState
import com.example.mazadytask.presentation.ui.theme.AppColors
import com.example.mazadytask.presentation.ui.theme.LocalLaunchColors
import com.example.mazadytask.presentation.ui.theme.MazadyAppTheme

@Composable
fun DetailsContent(
    state: LaunchDetailsState,
    paddingValues: PaddingValues = PaddingValues()
) {
    val colors = LocalLaunchColors.current
    val details = state.details ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.surfaceBackground)
            .padding(paddingValues)
            .padding(horizontal = 20.dp)
    ) {

        Spacer(Modifier.height(16.dp))

        // Mission Patch
        AsyncImage(
            model = details.missionPatchLarge,
            contentDescription = "Mission Patch",
            modifier = Modifier
                .fillMaxWidth(fraction = 0.8f)
                .heightIn(min = 220.dp , max = 320.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.height(32.dp))

        // Rocket Section
        SectionTitle("Rocket", colors)
        SectionItem("NAME", details.rocketDetails?.name ?: "Unknown", colors)
        SectionItem("TYPE", details.rocketDetails?.type ?: "N/A", colors)
        SectionItem("ID", details.rocketDetails?.id ?: "N/A", colors)

        Spacer(Modifier.height(28.dp))

        // Mission Section
        SectionTitle("Mission", colors)
        SectionItem("NAME", details.missionName, colors)

        Spacer(Modifier.height(28.dp))

        // Site Section
        SectionTitle("Site", colors)
        SectionItem(details.site ?: "Unknown", "", colors, isSingleLine = true)
    }
}



@Composable
private fun SectionTitle(
    text: String,
    colors: AppColors
) {
    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = colors.primaryText,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}


@Composable
private fun SectionItem(
    label: String,
    value: String,
    colors: AppColors,
    isSingleLine: Boolean = false
) {
    Column(modifier = Modifier.padding(bottom = 10.dp)) {

        if (!isSingleLine) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = colors.secondaryText,
                letterSpacing = 0.5.sp
            )
        }

        Text(
            text = if (isSingleLine) label else value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = colors.primaryText
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true,showSystemUi = true)
@Composable
fun DetailsContentPreview(){
    MazadyAppTheme {
        DetailsContent(
            state = LaunchDetailsState()
        )
    }
}