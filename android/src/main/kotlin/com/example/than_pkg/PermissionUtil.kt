package com.example.than_pkg

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings

/**
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />
 */

@Throws(Exception::class)
fun checkCanRequestPackageInstallsPermission(ctx: Activity): Boolean {
	var res = false
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
		if (!ctx.packageManager.canRequestPackageInstalls()) {
			val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
				.setData(Uri.parse("package:" + ctx.packageName))
			ctx.startActivityForResult(intent, 50000)
			// Request code for later handling
		} else {
			res = true
		}
	}
	return res
}

@SuppressLint("NewApi")
@Throws(Exception::class)
fun requestStoragePermission(ctx: Activity) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
		// 11 and above
		try {
			val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
			val uri = Uri.fromParts("package", ctx.packageName, null)
			intent.data = uri
			ctx.startActivity(intent)
		} catch (e: Exception) {
			val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
			ctx.startActivityForResult(intent, 101)
		}
	} else {
		// Android 6.0 to Android 10 (API 23 to 29)
		ctx.requestPermissions(
			arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
			101
		)
	}
}

@SuppressLint("NewApi")
fun isStoragePermissionGranted(ctx: Activity): Boolean {
	var granted = false
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
		// 11 and above
		if (Environment.isExternalStorageManager()) {
			granted = true
		}
	} else {
		// Android 6.0 to Android 10 (API 23 to 29)
		if (ctx.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
			granted = true
		}
	}
	return granted
}


