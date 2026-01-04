package com.example.mazadytask.presentation.screens.composables.paging

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.mazadytask.R
import com.example.mazadytask.presentation.ui.theme.LocalLaunchColors
import com.example.mazadytask.presentation.utils.spacing.AppSpacing

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    val colors = LocalLaunchColors.current


    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(colors.surfaceBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(AppSpacing.space_24)
        ) {
            Text(
                text = stringResource(R.string.no_launches_available),
                style = MaterialTheme.typography.titleLarge,
                color = colors.secondaryText,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(AppSpacing.space_8))

            Text(
                text = stringResource(R.string.check_back_later),
                style = MaterialTheme.typography.bodyMedium,
                color = colors.secondaryText,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun EmptyStatePreview(){
    EmptyState(
        paddingValues = PaddingValues()
    )
}