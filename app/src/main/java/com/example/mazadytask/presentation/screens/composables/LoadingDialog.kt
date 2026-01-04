package com.example.mazadytask.presentation.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mazadytask.presentation.ui.theme.LocalLaunchColors
import com.example.mazadytask.presentation.ui.theme.MazadyAppTheme
import com.example.mazadytask.presentation.utils.spacing.AppSpacing

@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onDismiss: () -> Unit = {}
) {
    val appColors = LocalLaunchColors.current

    if (visible) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            ),
            content = {
                CircularProgressIndicator(
                    modifier = modifier
                        .clip(RoundedCornerShape(AppSpacing.space_8))
                        .background(appColors.white)
                        .padding(AppSpacing.space_12)
                        .size(AppSpacing.space_32),
                    color = appColors.primaryBlue,
                    strokeCap = StrokeCap.Round
                )
            }
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoadingDialogPreview() {
    MazadyAppTheme {
        LoadingDialog(visible = true)
    }
}