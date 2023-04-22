package com.example.cameracompose.ui.components.camera

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.cameracompose.R
import com.example.cameracompose.ui.components.camera.viewmodel.CameraViewModel
import com.example.cameracompose.ui.components.gallery.GalleryScreen
import java.io.File


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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FloatingActionButton(
                onClick = { viewModel.onCaptureButtonClicked(context) },
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
