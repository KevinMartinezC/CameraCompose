package com.example.cameracompose.ui.components

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.cameracompose.ui.components.navigationbar.BottomBar
import com.example.cameracompose.ui.components.navigationbar.BottomNavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val showBottomBar = remember { mutableStateOf(true) }

    Scaffold(bottomBar = {
        if (showBottomBar.value) {
            BottomBar(navController = navController)
        }
    }) {
        BottomNavGraph(navController = navController, showBottomBar = showBottomBar)
    }
}
