package com.example.cameracompose.ui.components.detail

import android.content.ContentValues.TAG
import android.location.Location
import android.os.Parcelable
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.exifinterface.media.ExifInterface
import coil.compose.rememberImagePainter
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
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

    Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
        Image(
            painter = painter,
            contentDescription = "Detailed Image",
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = locationText,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
        CustomGoogleMap(
            modifier = Modifier
                .weight(0.5f)
                .padding(8.dp),
            initialLocation = location ?: Location("").apply {
                latitude = 1.35
                longitude = 103.87
            }
        )
    }
}

@Composable
fun CustomGoogleMap(modifier: Modifier = Modifier, initialLocation: Location) {
    val singapore = LatLng(initialLocation.latitude, initialLocation.longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    Card(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = singapore),
                title = "Selected location",
                snippet = "Marker at selected location"
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
