package com.example.cameracompose.ui.components.camera

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cameracompose.ui.components.viewmodel.CameraViewModel


@Composable
fun CameraScreen(
    onGalleryClicked: () -> Unit,
    viewModel: CameraViewModel,
    context: Context
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PreviewView(
            modifier = Modifier.fillMaxSize(),
            viewModel = viewModel
        )
        CameraFABs(
            onCaptureButtonClicked = { viewModel.onCaptureButtonClicked(context) },
            onGalleryClicked = onGalleryClicked
        )
    }
}

