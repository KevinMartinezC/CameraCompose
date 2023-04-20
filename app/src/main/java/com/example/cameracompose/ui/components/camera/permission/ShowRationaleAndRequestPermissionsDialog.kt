package com.example.cameracompose.ui.components.camera.permission

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.cameracompose.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShowRationaleAndRequestPermissionsDialog(
    showDialog: MutableState<Boolean>,
    permissionStates: List<PermissionState>,
    onGalleryClicked: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        title = {
            Text(
                stringResource(R.string.permission_required),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = { Text(stringResource(R.string.require_permission_to_function_properly)) },
        confirmButton = {
            TextButton(
                onClick = {
                    showDialog.value = false
                    permissionStates.forEach { it.launchPermissionRequest() }
                }
            ) {
                Text(stringResource(R.string.ok))
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
