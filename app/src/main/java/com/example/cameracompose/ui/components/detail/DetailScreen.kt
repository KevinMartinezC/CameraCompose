package com.example.cameracompose.ui.components.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.cameracompose.R
import com.example.cameracompose.ui.components.viewmodel.CameraViewModel
import java.io.File

@Composable
fun DetailScreen(name: String, viewModel: CameraViewModel) {
    val imagePath = viewModel.getImagePath(name)
    val imageFile = viewModel.getImageFile(name)
    val location = viewModel.getLocationFromImage(imageFile)

    val painter = rememberImagePainter(
        data = File(imagePath),
        builder = {
            crossfade(true)
        }
    )

    Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.detailed_image),
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )
        if (location != null) {
            CustomGoogleMap(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(8.dp),
                initialLocation = location
            )
        }
    }
}


