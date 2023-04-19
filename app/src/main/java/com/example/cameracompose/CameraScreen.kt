package com.example.cameracompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun CameraScreen(onCaptureClicked: () -> Unit, onGalleryClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PreviewView(modifier = Modifier.fillMaxSize())

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FloatingActionButton(
                onClick = onCaptureClicked,

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

@Preview
@Composable
fun CameraScreenPreview() {
    CameraScreen(onCaptureClicked = {}, onGalleryClicked = {})
}

@Composable
fun PreviewView(modifier: Modifier = Modifier) {
    // Implement your camera preview here
}
