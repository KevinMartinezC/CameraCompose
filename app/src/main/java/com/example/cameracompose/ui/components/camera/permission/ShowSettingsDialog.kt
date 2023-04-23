package com.example.cameracompose.ui.components.camera.permission

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
import com.example.cameracompose.ui.components.viewmodel.CameraViewModel

@Composable
fun ShowSettingsDialog(
    showDialog: MutableState<Boolean>,
    onGalleryClicked: () -> Unit,
    viewModel: CameraViewModel
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
                    viewModel.openAppSettings(context)
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

