package com.example.cameracompose

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cameracompose.ui.theme.CameraComposeTheme
import com.example.cameracompose.ui.theme.viewmodel.CameraViewModel

class MainActivity : ComponentActivity() {

    private val cameraViewModel by viewModels<CameraViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraScreenComposable(
                viewModel = cameraViewModel,
                onGalleryClicked = {}
            )
        }
    }

}

