package com.example.cameracompose.ui.components.navigationbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cameracompose.ui.components.camera.permission.CameraScreenComposable
import com.example.cameracompose.ui.components.gallery.GalleryScreen
import com.example.cameracompose.ui.components.camera.viewmodel.CameraViewModel


@Composable
fun BottomNavGraph(navController: NavHostController, showBottomBar: MutableState<Boolean>) {
    val cameraViewModel = remember { CameraViewModel() }

    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Gallery.route
    ) {
        composable(route = BottomNavItem.Gallery.route) {
            GalleryScreen(cameraViewModel)
        }
        composable(route = BottomNavItem.Camera.route) {
            CameraScreenComposable(
                viewModel = cameraViewModel,
                onGalleryClicked = { navController.navigate(BottomNavItem.Gallery.route) }
            )
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        showBottomBar.value = currentRoute != BottomNavItem.Camera.route
    }
}


