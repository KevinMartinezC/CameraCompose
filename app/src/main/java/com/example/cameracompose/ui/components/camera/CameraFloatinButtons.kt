package com.example.cameracompose.ui.components.camera

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.cameracompose.R


@Composable
fun CameraFABs(
    onCaptureButtonClicked: () -> Unit,
    onGalleryClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.padding_16dp))
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_32dp))
        ) {
            FloatingActionButton(
                onClick = onCaptureButtonClicked,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.outline_camera_24),
                    contentDescription = null
                )
            }
            FloatingActionButton(
                onClick = onGalleryClicked,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.outline_photo_24),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CameraFABsPreview() {
    CameraFABs(
        onCaptureButtonClicked = { /* No action */ },
        onGalleryClicked = { /* No action */ }
    )
}