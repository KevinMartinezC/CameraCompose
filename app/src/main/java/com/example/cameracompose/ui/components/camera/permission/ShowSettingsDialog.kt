package com.example.cameracompose.ui.components.camera.permission

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.cameracompose.R

@Composable
fun ShowSettingsDialog(
    showDialog: MutableState<Boolean>,
    onGalleryClicked: () -> Unit
) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        title = {
            Text(
                stringResource(R.string.permission_required),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = { Text(stringResource(R.string.go_to_settings)) },
        confirmButton = {
            TextButton(
                onClick = {
                    showDialog.value = false
                    openAppSettings(context)
                }
            ) {
                Text(stringResource(R.string.settings))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    showDialog.value = false
                    onGalleryClicked()
                }
            ) {
                Text(stringResource(R.string.close))
            }
        }
    )
}
fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}