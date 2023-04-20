package com.example.cameracompose

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import java.util.concurrent.Executors
import androidx.camera.core.Preview
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.cameracompose.ui.theme.viewmodel.CameraViewModel
import java.text.SimpleDateFormat
import java.util.Locale


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

@Composable
fun PreviewView(
    modifier: Modifier = Modifier,
    viewModel: CameraViewModel // Add viewModel parameter
) {
    LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    val imageCapture = remember { mutableStateOf<ImageCapture?>(null) }

    AndroidView(
        factory = { context ->
            val previewView = androidx.camera.view.PreviewView(context)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                imageCapture.value = ImageCapture.Builder()
                    .build()

                viewModel.imageCapture = imageCapture.value // Set imageCapture in the ViewModel

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, preview, imageCapture.value
                    )
                } catch (exc: Exception) {
                    Log.e("CameraXApp", "Use case binding failed", exc)
                }

            }, ContextCompat.getMainExecutor(context))

            previewView
        },
        modifier = modifier
    )

    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }
}
