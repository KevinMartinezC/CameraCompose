package com.example.cameracompose.ui.components.camera.permission

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.example.cameracompose.ui.components.camera.viewmodel.CameraViewModel.Companion.PACKAGE

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts(PACKAGE, context.packageName, null)
    }
    context.startActivity(intent)
}