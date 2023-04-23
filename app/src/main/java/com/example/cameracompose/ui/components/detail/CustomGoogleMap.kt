package com.example.cameracompose.ui.components.detail

import android.location.Location
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.cameracompose.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun CustomGoogleMap(modifier: Modifier = Modifier, initialLocation: Location) {
    val locationPhoto = LatLng(initialLocation.latitude, initialLocation.longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(locationPhoto, 10f)
    }
    Card(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_8dp)),
        border = BorderStroke(dimensionResource(R.dimen.border_stroke_1dp), MaterialTheme.colorScheme.primary),
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = locationPhoto),
                title = stringResource(R.string.selected_location),
                snippet = stringResource(R.string.marker_at_selected_location)
            )
        }
    }
}

