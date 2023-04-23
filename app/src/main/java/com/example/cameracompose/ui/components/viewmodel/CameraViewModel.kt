package com.example.cameracompose.ui.components.viewmodel

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cameracompose.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

class CameraViewModel : ViewModel() {
    var imageCapture: ImageCapture? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    fun initLocationProvider(context: Context) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    }

    val requiredPermissions = mutableListOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    ).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(android.Manifest.permission.READ_MEDIA_IMAGES)
        }
    }.toTypedArray()

    @SuppressLint("MissingPermission")
    private fun takePhoto(context: Context) {
        val imageCapture = imageCapture ?: return

        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, JPEG_MIME_TYPE)
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, CAMERAX_IMAGE_FOLDER)
            }
        }

        // Get the last known location
        viewModelScope.launch {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                val metadata = ImageCapture.Metadata().apply {
                    this.location = location
                }

                val outputOptions = ImageCapture.OutputFileOptions
                    .Builder(
                        context.contentResolver,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                    )
                    .setMetadata(metadata)
                    .build()

                imageCapture.takePicture(
                    outputOptions,
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onError(exc: ImageCaptureException) {
                            Log.e(TAG, context.getString(R.string.photo_capture_failed, exc.message), exc)
                        }

                        override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                            val msg = context.getString(R.string.photo_capture_succeeded, output.savedUri)
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            Log.d(TAG, msg)
                        }
                    }
                )
            }
        }
    }

    fun onCaptureButtonClicked(context: Context) {
        takePhoto(context)
    }

    fun getImagesFromGallery(): StateFlow<List<File>> {
        val imagesStateFlow = MutableStateFlow<List<File>>(emptyList())
        val imageFolder = File(Environment.getExternalStorageDirectory(), CAMERAX_IMAGE_FOLDER)
        val imageFiles = imageFolder.listFiles()?.filter { it.extension == IMAGE_EXTENSION }?.toList() ?: emptyList()

        imagesStateFlow.value = imageFiles
        return imagesStateFlow
    }

    fun getLocationFromImage(file: File): Location? {
        return try {
            val exifInterface = ExifInterface(file.path)
            val latLong = exifInterface.latLong

            if (latLong != null) {
                Location("").apply {
                    latitude = latLong[0]
                    longitude = latLong[1]
                }
            } else {
                null
            }
        } catch (e: IOException) {
            Log.e(ContentValues.TAG, "Error reading EXIF data", e)
            null
        }
    }

    fun getImagePath(name: String): String {
        return IMAGE_PATH + name
    }

    fun getImageFile(name: String): File {
        return File(IMAGE_PATH + name)
    }

    fun openAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts(PACKAGE, context.packageName, null)
        }
        context.startActivity(intent)
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val CAMERAX_IMAGE_FOLDER = "Pictures/CameraX-Image"
        private const val JPEG_MIME_TYPE = "image/jpeg"
        private const val IMAGE_EXTENSION = "jpg"
        const val PACKAGE = "package"
        const val IMAGE_PATH = "/storage/emulated/0/Pictures/CameraX-Image/"

    }
}
