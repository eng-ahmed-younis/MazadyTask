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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun ErrorDialog(
    visible: Boolean,
    message: String,
    title: String? = null ,
    onDismiss: () -> Unit,
) {
    CustomConfirmDialog(
        visible = visible,
        title = title ?: "Error",
        message = message,
        confirmText = "OK",
        dismissText = "Cancel", // or keep it, or hide it if you add a flag
        onConfirm = onDismiss,
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
    dismissText: String = "Cancel",
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
            usePlatformDefaultWidth = false // مهم: عشان نتحكم في التمركز/العرض
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                tonalElevation = 6.dp,
                shadowElevation = 14.dp,
                modifier = Modifier
                    .fillMaxWidth(0.9f)      // عرض مناسب في النص
                    .wrapContentHeight()     // ارتفاع حسب المحتوى
            ) {
                Column(modifier = Modifier.padding(24.dp)) {

                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text(dismissText)
                        }

                        Spacer(Modifier.width(12.dp))

                        Button(
                            onClick = onConfirm,
                            shape = RoundedCornerShape(999.dp),
                            contentPadding = PaddingValues(horizontal = 22.dp, vertical = 10.dp)
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