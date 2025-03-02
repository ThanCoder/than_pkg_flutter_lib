package than.plugin.than_pkg

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
import android.content.res.Configuration
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.provider.Settings

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import than.plugin.than_pkg.AppUtil.checkOrientation
import than.plugin.than_pkg.AppUtil.hideFullScreen
import than.plugin.than_pkg.AppUtil.showFullScreen
import than.plugin.than_pkg.AppUtil.toggleKeepScreenOn
import than.plugin.than_pkg.PdfUtil.genPdfCoverList
import than.plugin.than_pkg.PermissionUtil.isStoragePermissionGranted
import than.plugin.than_pkg.PermissionUtil.requestStoragePermission
import than.plugin.than_pkg.WifiUtil.getLocalIpAddress
import than.plugin.than_pkg.WifiUtil.getWifiAddress
import than.plugin.than_pkg.WifiUtil.getWifiAddressList

/** ThanPkgPlugin */
class ThanPkgPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
	/// The MethodChannel that will the communication between Flutter and native Android
	///
	/// This local reference serves to register the plugin with the Flutter Engine and unregister it
	/// when the Flutter Engine is detached from the Activity
	private lateinit var channel: MethodChannel
	private lateinit var context: Context
	private var activity: Activity? = null

	override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
		channel = MethodChannel(flutterPluginBinding.binaryMessenger, "than_pkg")
		channel.setMethodCallHandler(this)
		context = flutterPluginBinding.applicationContext
	}

	override fun onAttachedToActivity(binding: ActivityPluginBinding) {
		activity = binding.activity
		//listen activity result
		binding.addActivityResultListener { reqCode, resultCode, data ->
			channel.invokeMethod(
				"onActivityResult", mapOf(
					"requestCode" to reqCode,
					"resultCode" to resultCode,
					"data" to data?.toString(),
				)
			)
			true
		}
	}


	@SuppressLint("HardwareIds", "NewApi", "SourceLockedOrientationActivity", "MissingPermission")
	override fun onMethodCall(call: MethodCall, result: Result) {
		when (call.method) {

			"openUrl" -> {
				try {
					val url = call.argument<String>("url") ?: ""
					AppUtil.openUrl(context, url)
					result.success(true)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"hideFullScreen" -> {
				activity?.let {
					AppUtil.hideFullScreen(it.window, context)
				}
				result.success(true)
			}

			"showFullScreen" -> {
				activity?.let { AppUtil.showFullScreen(it.window) }
				result.success(true)
			}

			"getInstalledAppsList" -> {
				try {
					val packageManager = context.packageManager
					val packages = packageManager?.getInstalledApplications(0)?.map {
						mapOf(
							"packageName" to it.packageName,
							"appName" to packageManager.getApplicationLabel(it).toString()
						)
					}
					result.success(packages)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"getBatteryLevel" -> {
				try {
					val res = AppUtil.getBatteryLevel(context)
					result.success(res)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"isInternetConnected" -> {
				try {
					val connectivityManager =
						context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
					val networkInfo = connectivityManager.activeNetworkInfo
					result.success(networkInfo?.isConnected == true)

				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"isDarkModeEnabled" -> {
				try {
					val isDarkMode = AppUtil.isDarkModeEnabled(context)
					result.success(isDarkMode)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"getFilesDir" -> {
				try {
					val res = context.filesDir
					result.success(res.path)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"getExternalFilesDir" -> {
				try {
					val res = context.getExternalFilesDir(null)
					result.success(res?.path)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"getAppExternalPath" -> {
				try {
					result.success("/storage/emulated/0")
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}
			//orientation
			"requestOrientation" -> {
				try {
					val type = call.argument<String>("type") ?: "Portrait"
					val isReverse = call.argument<Boolean>("reverse") ?: false
					activity?.let {
						AppUtil.requestOrientation(it, isReverse = isReverse, type = type)
					}
					result.success("")
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"checkOrientation" -> {
				try {
					val res = checkOrientation(context)
					result.success(res)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"getDeviceInfo" -> {
				try {
					val obj = AppUtil.getDeviceInfo()
					result.success(obj)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"getPlatformVersion" -> {
				result.success(android.os.Build.VERSION.RELEASE)
			}

			"toggleKeepScreenOn" -> {
				try {
					val isKeep = call.argument<Boolean>("is_keep") ?: false
					activity?.let { toggleKeepScreenOn(it.window, isKeep) }
					result.success("")
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"getDeviceId" -> {
				try {
					val androidId = AppUtil.getDeviceId(context)
					result.success(androidId)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			//permission
			"checkCanRequestPackageInstallsPermission" -> {
				try {
					activity?.let {
						PermissionUtil.checkCanRequestPackageInstallsPermission(it)
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
						val res = PermissionUtil.isPackageInstallPermission(it)
						result.success(res)
					}
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"isStoragePermissionGranted" -> {
				try {
					activity?.let {
						val res = PermissionUtil.isStoragePermissionGranted(it)
						result.success(res)
					}
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"isCameraPermission" -> {
				try {
					activity?.let {
						val res = PermissionUtil.isCameraPermission(it)
						result.success(res)
					}
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"isLocationPermission" -> {
				try {
					activity?.let {
						val res = PermissionUtil.isLocationPermission(it)
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
						PermissionUtil.requestStoragePermission(it)
						result.success("")
					}

				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"requestPackageInstallPermission" -> {
				try {
					activity?.let {
						PermissionUtil.requestPackageInstallPermission(it)
						result.success("")
					}

				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"requestCameraPermission" -> {
				try {
					activity?.let {
						PermissionUtil.requestCameraPermission(it)
						result.success("")
					}
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"requestLocationPermission" -> {
				try {
					activity?.let {
						PermissionUtil.requestLocationPermission(it)
						result.success("")
					}
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			//wifi
			"getWifiSSID" -> {
				try {
					val wifiManager =
						context.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager
					val wifiInfo = wifiManager.connectionInfo
					result.success(wifiInfo.ssid)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"getLocalIpAddress" -> {
				try {
					val res = getLocalIpAddress()
					result.success(res)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"getWifiAddress" -> {
				try {
					val res = getWifiAddress()
					result.success(res)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"getWifiAddressList" -> {
				try {
					val res = getWifiAddressList()
					result.success(res)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			//video thumbnail
			"genVideoThumbnailList" -> {
				try {
					val pathList = call.argument<List<String>>("path_list") ?: listOf()
					val outDirPath = call.argument<String>("out_dir_path") ?: ""

					VideoUtil.genVideoThumbnailList(outDirPath = outDirPath,
						pathList = pathList,
						onCreated = {
							result.success("")
						},
						onError = { err ->
							result.error("ERROR", err.toString(), err)
						})
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"genVideoThumbnail" -> {
				try {
					val videoPath = call.argument<String>("video_path") ?: ""
					val outPath = call.argument<String>("out_path") ?: ""

					VideoUtil.genVideoThumbnail(videoPath = videoPath,
						outPath = outPath,
						onCreated = { savedPath ->
							result.success(savedPath)
						},
						onError = { err ->
							result.error("ERROR", err.toString(), err)
						})


				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}
			//pdf util
			"genPdfCoverList" -> {
				val outDirPath = call.argument<String>("out_dir_path")
				val pdfListPath = call.argument<List<String>>("pdf_path_list")
				val size = call.argument<Int>("size") ?: 300

				if (outDirPath == null || pdfListPath == null) {
					result.error(
						"ERROR",
						"outDirPath == null || pdfListPath == null",
						"val outDirPath = call.argument<String>(\"\")\n" + "        val pdfListPath = call.argument<List<String>>(\"\")\n" + "        val size = call.argument<Int>(\"\")"
					)
					return
				}
				PdfUtil.genPdfCoverList(pathList = pdfListPath,
					outPath = outDirPath,
					size = size,
					onLoaded = {
						result.success("success")
					},
					onError = { err ->
						result.error("ERROR", err.toString(), err)
					})

			}

			"genPdfImage" -> {
				val pdfPath = call.argument<String>("pdf_path") ?: ""
				val outPath = call.argument<String>("out_path") ?: ""
				val pageIndex = call.argument<Int>("page_index") ?: 0
				val size = call.argument<Int>("size") ?: -1
				PdfUtil.genPdfThumbnail(pdfPath = pdfPath,
					thumbnailPath = outPath,
					size = size,
					pageIndex = pageIndex,
					onLoaded = { savedPath ->
						result.success(savedPath)
					},
					onError = { err ->
						result.error("ERROR", err.toString(), err)
					})
			}

			"getPdfPageCount" -> {
				val pdfPath = call.argument<String>("pdf_path") ?: ""
				PdfUtil.getPdfPageCount(pdfPath = pdfPath, onLoaded = { pageCount ->
					result.success(pageCount)
				}, onError = { err ->
					result.error("ERROR", err.toString(), err)
				})
			}
			//camera
			"openCamera" -> {
				activity?.let {
					CameraUtil.openCamera(it)
				}
				result.success("")
			}

			else -> {
				result.notImplemented()
			}
		}
	}

	override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
		channel.setMethodCallHandler(null)
	}

	override fun onDetachedFromActivityForConfigChanges() {
		TODO("Not yet implemented")
	}

	override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
		TODO("Not yet implemented")
	}

	override fun onDetachedFromActivity() {
		TODO("Not yet implemented")
	}
}
