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
import com.example.cameracompose.ui.components.viewmodel.CameraViewModel
import com.example.cameracompose.ui.components.detail.DetailScreen
import com.example.cameracompose.ui.components.viewmodel.CameraViewModel.Companion.DETAIL_ROUTE
import com.example.cameracompose.ui.components.viewmodel.CameraViewModel.Companion.ROUTE_ARGUMENT_IMAGE_KEY


@Composable
fun BottomNavGraph(navController: NavHostController, showBottomBar: MutableState<Boolean>) {
    val cameraViewModel = remember { CameraViewModel() }

    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Gallery.route
    ) {
        composable(route = BottomNavItem.Gallery.route) {
            GalleryScreen(
                cameraViewModel, navController
            )
        }
        composable(route = BottomNavItem.Camera.route) {
            CameraScreenComposable(
                viewModel = cameraViewModel,
                onGalleryClicked = { navController.navigate(BottomNavItem.Gallery.route) }
            )
        }
        composable(route = DETAIL_ROUTE) { entry ->
            val name = entry.arguments?.getString(ROUTE_ARGUMENT_IMAGE_KEY).orEmpty()
            DetailScreen(
                name = name,
                viewModel = cameraViewModel
            )
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        showBottomBar.value = currentRoute != BottomNavItem.Camera.route
    }
}


