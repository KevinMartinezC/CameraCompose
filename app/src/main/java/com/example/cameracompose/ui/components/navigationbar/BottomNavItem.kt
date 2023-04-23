package com.example.cameracompose.ui.components.navigationbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Gallery : BottomNavItem(
        route = "gallery",
        title = "Gallery",
        icon = Icons.Default.AddPhotoAlternate
    )

    object Camera : BottomNavItem(
        route = "home",
        title = "Home",
        icon = Icons.Default.AddAPhoto
    )
}
