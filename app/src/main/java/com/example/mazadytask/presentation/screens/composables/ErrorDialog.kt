package com.example.mazadytask.presentation.screens.composables

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mazadytask.presentation.ui.theme.MazadyAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mazadytask.R
import com.example.mazadytask.presentation.utils.spacing.AppSpacing


@Composable
fun ErrorDialog(
    visible: Boolean,
    message: String,
    title: String? = null ,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    CustomConfirmDialog(
        visible = visible,
        title = title ?: stringResource(R.string.dialog_error_title),
        message = message,
        confirmText = stringResource(R.string.action_ok),
        dismissText = stringResource(R.string.action_cancel),
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        dismissOnOutsideClick = true
    )
}

@Composable
fun CustomConfirmDialog(
    visible: Boolean,
    title: String,
    message: String,
    confirmText: String,
    dismissText: String = stringResource(R.string.action_cancel),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    dismissOnOutsideClick: Boolean = true
) {
    if (!visible) return

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = dismissOnOutsideClick,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = RoundedCornerShape(AppSpacing.space_24),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                tonalElevation = AppSpacing.space_8,
                shadowElevation = AppSpacing.space_12,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier.padding(AppSpacing.space_24)
                ) {

                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(AppSpacing.space_12))

                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(AppSpacing.space_24))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {

                        TextButton(onClick = onDismiss) {
                            Text(dismissText)
                        }

                        Spacer(modifier = Modifier.width(AppSpacing.space_12))

                        Button(
                            onClick = onConfirm,
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(
                                horizontal = AppSpacing.space_24,
                                vertical = AppSpacing.space_12
                            )
                        ) {
                            Text(confirmText)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ErrorDialogPreview(){

    MazadyAppTheme {
        CustomConfirmDialog(
            visible = true,
            message = "Error message",
            onDismiss = {},
            onConfirm = {},
            title = "Error",
            confirmText = "OK",
            dismissText = "Cancel"
        )
    }
}