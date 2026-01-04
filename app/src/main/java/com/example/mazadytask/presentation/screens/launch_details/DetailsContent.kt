package com.example.mazadytask.presentation.screens.launch_details

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.mazadytask.R
import com.example.mazadytask.presentation.screens.launch_details.mvi.LaunchDetailsState
import com.example.mazadytask.presentation.ui.theme.AppColors
import com.example.mazadytask.presentation.ui.theme.LocalLaunchColors
import com.example.mazadytask.presentation.ui.theme.MazadyAppTheme
import com.example.mazadytask.presentation.utils.spacing.AppSpacing

@Composable
fun DetailsContent(
    modifier: Modifier = Modifier,
    state: LaunchDetailsState,
    paddingValues: PaddingValues = PaddingValues()
) {
    val colors = LocalLaunchColors.current
    val details = state.details ?: return

    Column(
        modifier = modifier
            .fillMaxSize()
            .scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Vertical
            )
            .background(colors.surfaceBackground)
            .padding(paddingValues)
            .padding(horizontal = 20.dp)
    ) {

        Spacer(Modifier.height(16.dp))

        AsyncImage(
            model = details.missionPatchLarge,
            contentDescription = stringResource(R.string.mission_patch),
            modifier = Modifier
                .fillMaxWidth(fraction = 0.8f)
                .heightIn(min = 220.dp, max = 320.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.height(32.dp))

        SectionTitle(
            text = stringResource(R.string.section_rocket),
            colors = colors
        )
        SectionItem(
            label = stringResource(R.string.label_name),
            value = details.rocketDetails?.name ?: stringResource(R.string.value_unknown),
            colors = colors
        )
        SectionItem(
            label = stringResource(R.string.label_type),
            value = details.rocketDetails?.type ?: stringResource(R.string.value_not_available),
            colors = colors
        )
        SectionItem(
            label = stringResource(R.string.label_id),
            value = details.rocketDetails?.id ?: stringResource(R.string.value_not_available),
            colors = colors
        )

        Spacer(Modifier.height(28.dp))


        SectionTitle(
            text = stringResource(R.string.section_mission),
            colors = colors
        )

        SectionItem(
            label = stringResource(R.string.label_name),
            value = details.missionName,
            colors = colors
        )

        Spacer(Modifier.height(28.dp))


        SectionTitle(
            text = stringResource(R.string.section_site),
            colors = colors

        )

        SectionItem(
            label = details.site ?: stringResource(R.string.value_unknown),
            value = "",
            colors = colors,
            isSingleLine = true
        )
    }
}


@Composable
private fun SectionTitle(
    modifier: Modifier = Modifier,
    text: String,
    colors: AppColors
) {
    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = colors.primaryText,
        modifier = modifier
            .padding(bottom = AppSpacing.space_8)
    )
}


@Composable
private fun SectionItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    colors: AppColors,
    isSingleLine: Boolean = false
) {
    Column(
        modifier = modifier
            .padding(bottom = AppSpacing.space_12)
    ) {

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
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailsContentPreview() {
    MazadyAppTheme {
        DetailsContent(
            state = LaunchDetailsState()
        )
    }
}