package com.example.cameracompose

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.cameracompose.viewmodel.CameraViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreenComposable(
    viewModel: CameraViewModel,
    onGalleryClicked: () -> Unit,

) {
    val context = LocalContext.current
    val requiredPermissions = remember {
        mutableListOf(android.Manifest.permission.CAMERA).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(android.Manifest.permission.READ_MEDIA_IMAGES)
            }
        }.toTypedArray()
    }

    val allPermissionsGranted = requiredPermissions.all { permission ->
        rememberPermissionState(permission).hasPermission
    }

    if (allPermissionsGranted) {

        CameraScreen(
            viewModel = viewModel,
            context = context,
            onGalleryClicked = {
                onGalleryClicked()
            }
        )
    } else {
        Column {
            Text("The following permissions are required:")
            requiredPermissions.forEach { permission ->
                val permissionState = rememberPermissionState(permission)
                Text("- $permission: ${if (permissionState.hasPermission) "Granted" else "Not granted"}")

                if (!permissionState.hasPermission) {
                    Button(onClick = { permissionState.launchPermissionRequest() }) {
                        Text("Request Permission")
                    }
                }
            }
        }
    }
}
