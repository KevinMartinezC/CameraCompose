package com.example.cameracompose.ui.components.camera.permission

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.cameracompose.ui.components.camera.CameraScreen
import com.example.cameracompose.ui.components.viewmodel.CameraViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreenComposable(
    viewModel: CameraViewModel,
    onGalleryClicked: () -> Unit
) {
    val context = LocalContext.current
    val permissionStates = viewModel.requiredPermissions.map { rememberPermissionState(it) }
    val allPermissionsGranted = permissionStates.all { it.hasPermission }
    val showDialog = remember { mutableStateOf(false) }
    val showSettingsDialog = remember { mutableStateOf(false) }

    if (allPermissionsGranted) {
        viewModel.initLocationProvider(context)
        CameraScreen(
            viewModel = viewModel,
            context = context,
            onGalleryClicked = onGalleryClicked
        )
    } else {
        if (permissionStates.any { it.shouldShowRationale }) {
            showDialog.value = true
        } else if (permissionStates.any { it.permissionRequested }) {
            showSettingsDialog.value = true
        } else {
            LaunchedEffect(Unit) {
                permissionStates.forEach { it.launchPermissionRequest() }
            }
        }

        if (showDialog.value) {
            ShowRationaleAndRequestPermissionsDialog(
                showDialog = showDialog,
                permissionStates = permissionStates,
                onGalleryClicked = onGalleryClicked
            )
        } else if (showSettingsDialog.value) {
            ShowSettingsDialog(
                showDialog = showSettingsDialog,
                onGalleryClicked = onGalleryClicked,
                viewModel = viewModel
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

