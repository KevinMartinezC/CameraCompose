package com.example.cameracompose.ui.components.detail

import android.content.ContentValues
import android.location.Location
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.IOException

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
