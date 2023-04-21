package com.example.cameracompose.ui.components.detail

import android.content.ContentValues.TAG
import android.location.Location
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.exifinterface.media.ExifInterface
import coil.compose.rememberImagePainter
import java.io.File
import java.io.IOException

@Composable
fun DetailScreen(name: String) {
    val painter = rememberImagePainter(
        data = File("/storage/emulated/0/Pictures/CameraX-Image/${name}"),
        builder = {
            crossfade(true)
        }
    )

    val imageFile = File("/storage/emulated/0/Pictures/CameraX-Image/${name}")
    val location = getLocationFromImage(imageFile)
    val locationText = if (location != null) {
        "Latitude: ${location.latitude}, Longitude: ${location.longitude}"
    } else {
        "Location data not available"
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Image(
                painter = painter,
                contentDescription = "Detailed Image",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
            Text(
                text = locationText,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
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
        Log.e(TAG, "Error reading EXIF data", e)
        null
    }
}
