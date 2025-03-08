package than.plugin.than_pkg

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.Result

/**
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />
 */

object PermissionUtil {
	fun callCheck(call: MethodCall, result: Result, context: Context, activity: Activity?){
		val method = call.method.replace("permissionUtil/","")
		when(method) {
			//permission
			"checkCanRequestPackageInstallsPermission" -> {
				try {
					activity?.let {
						checkCanRequestPackageInstallsPermission(it)
						result.success("")
					}

				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}
			//is
			"isPackageInstallPermission" -> {
				try {
					activity?.let {
						val res = isPackageInstallPermission(it)
						result.success(res)
					}
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"isStoragePermissionGranted" -> {
				try {
					activity?.let {
						val res = isStoragePermissionGranted(it)
						result.success(res)
					}
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"isCameraPermission" -> {
				try {
					activity?.let {
						val res = isCameraPermission(it)
						result.success(res)
					}
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"isLocationPermission" -> {
				try {
					activity?.let {
						val res = isLocationPermission(it)
						result.success(res)
					}
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}
			//req
			"requestStoragePermission" -> {
				try {
					activity?.let {
						requestStoragePermission(it)
						result.success("")
					}

				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"requestPackageInstallPermission" -> {
				try {
					activity?.let {
						requestPackageInstallPermission(it)
						result.success("")
					}

				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"requestCameraPermission" -> {
				try {
					activity?.let {
						requestCameraPermission(it)
						result.success("")
					}
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"requestLocationPermission" -> {
				try {
					activity?.let {
						requestLocationPermission(it)
						result.success("")
					}
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}
		}
	}

	fun isCameraPermission(context: Context): Boolean {
		return (ContextCompat.checkSelfPermission(
			context, Manifest.permission.CAMERA
		) == PackageManager.PERMISSION_GRANTED)
	}
	/*
	<uses-feature
	android:name="android.hardware.camera"
	android:required="false" />
	<uses-permission android:name="android.permission.CAMERA"/>
	*/
	fun requestCameraPermission(activity: Activity) {
		// Request permissions if not granted
		ActivityCompat.requestPermissions(
			activity, arrayOf(Manifest.permission.CAMERA), 10003
		)
	}

	//location
	fun requestLocationPermission(activity: Activity) {
		// Request permissions if not granted
		ActivityCompat.requestPermissions(
			activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 10002
		)
	}

	fun isLocationPermission(context: Context): Boolean {
		return (ContextCompat.checkSelfPermission(
			context, Manifest.permission.ACCESS_FINE_LOCATION
		) == PackageManager.PERMISSION_GRANTED)
	}

	//	request
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
				ctx.startActivityForResult(intent, 10001)
			}
		} else {
			// Android 6.0 to Android 10 (API 23 to 29)
			ctx.requestPermissions(
				arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 101
			)
		}
	}

	//check permission
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

	// check && request //<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
	@Throws(Exception::class)
	fun isPackageInstallPermission(ctx: Activity): Boolean {
		var res = false
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			res = ctx.packageManager.canRequestPackageInstalls()
		}
		return res
	}

	fun requestPackageInstallPermission(ctx: Activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val intent =
				Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).setData(Uri.parse("package:" + ctx.packageName))
			ctx.startActivityForResult(intent, 10000)
			// Request code for later handling
		}
	}

	fun checkCanRequestPackageInstallsPermission(ctx: Activity){
		if(!isPackageInstallPermission(ctx)){
			requestPackageInstallPermission(ctx)
		}
	}


}


