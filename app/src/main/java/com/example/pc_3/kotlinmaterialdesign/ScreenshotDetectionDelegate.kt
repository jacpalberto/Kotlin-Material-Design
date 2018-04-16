package com.example.pc_3.kotlinmaterialdesign

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import java.lang.ref.WeakReference

/**
 * Created by Alberto Carrillo on 4/16/18.
 */
class ScreenshotDetectionDelegate(activityWeakReference: Activity, private val listener: ScreenshotDetectionListener?) {
    private val activityWeakReference: WeakReference<Activity> = WeakReference(activityWeakReference)

    private val contentObserver = object : ContentObserver(Handler()) {

        override fun onChange(selfChange: Boolean, uri: Uri) {
            super.onChange(selfChange, uri)
            if (isReadExternalStoragePermissionGranted) {
                val path = getFilePathFromContentResolver(activityWeakReference, uri)
                if (isScreenshotPath(path)) {
                    onScreenCaptured(path)
                }
            } else {
                onScreenCapturedWithDeniedPermission()
            }
        }
    }

    private val isReadExternalStoragePermissionGranted: Boolean
        get() = ContextCompat.checkSelfPermission(activityWeakReference.get()?.baseContext!!, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    fun startScreenshotDetection() {
        activityWeakReference.get()?.contentResolver?.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, contentObserver)
    }

    fun stopScreenshotDetection() {
        activityWeakReference.get()?.contentResolver?.unregisterContentObserver(contentObserver)
    }

    private fun onScreenCaptured(path: String?) {
        listener?.onScreenCaptured(path)
    }

    private fun onScreenCapturedWithDeniedPermission() {
        listener?.onScreenCapturedWithDeniedPermission()
    }

    private fun isScreenshotPath(path: String?): Boolean {
        return path != null && path.toLowerCase().contains("screenshots")
    }

    private fun getFilePathFromContentResolver(context: Context, uri: Uri): String? {
        try {
            val cursor = context.contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA), null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
                cursor.close()
                return path
            }
        } catch (ignored: IllegalStateException) {
        }

        return null
    }

    interface ScreenshotDetectionListener {
        fun onScreenCaptured(path: String?)

        fun onScreenCapturedWithDeniedPermission()
    }
}