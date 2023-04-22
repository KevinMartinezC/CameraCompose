package com.example.cameracompose.ui.components.gallery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cameracompose.R
import com.example.cameracompose.ui.components.camera.viewmodel.CameraViewModel

@Composable
fun GalleryScreen(cameraViewModel: CameraViewModel, navController: NavHostController) {
    val images = cameraViewModel.getImagesFromGallery().collectAsState(emptyList())

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (images.value.isEmpty()) {
            Text(text = stringResource(id = R.string.no_images_in_the_gallery), fontSize = 24.sp)
        } else {
            GalleryDesign(images = images.value,navController = navController
            )
        }
    }
}

