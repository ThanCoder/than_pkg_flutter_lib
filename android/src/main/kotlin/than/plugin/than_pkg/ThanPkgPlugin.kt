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
	}


	@SuppressLint("HardwareIds", "NewApi", "SourceLockedOrientationActivity", "MissingPermission")
	override fun onMethodCall(call: MethodCall, result: Result) {
		when (call.method) {
//			"" -> {
//				try {
//					val res = ""
//
//					result.success(res)
//				} catch (err: Exception) {
//					result.error("ERROR", err.toString(), err)
//				}
//			}
			"get_wifi_SSID" -> {
				try {

					val wifiManager =
						context.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager
					val wifiInfo = wifiManager.connectionInfo
					result.success(wifiInfo.ssid)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"get_installed_apps" -> {
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

			"get_last_known_location" -> {
				try {
					val locationManager =
						context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
					val location =
						locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
					if (location != null) {
						val locationData = mapOf(
							"latitude" to location.latitude,
							"longitude" to location.longitude
						)
						result.success(locationData)
					} else {
						result.error("LOCATION_ERROR", "Location not available", null)
					}
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"get_battery_level" -> {
				try {
					val batteryManager =
						context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
					val batteryLevel =
						batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
					result.success(batteryLevel)

				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"is_internet_connected" -> {
				try {

					val connectivityManager =
						context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
					val networkInfo = connectivityManager.activeNetworkInfo
					result.success(networkInfo?.isConnected == true)


				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"is_system_dark_mode" -> {
				try {
					val isDarkMode =
						(context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

					result.success(isDarkMode)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"get_app_file_path" -> {
				try {
					val res = context.filesDir
					result.success(res.path)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"get_app_root_path" -> {
				try {
					val res = context.getExternalFilesDir(null)
					result.success(res?.path)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"get_app_external_path" -> {
				try {
					result.success("/storage/emulated/0")
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"req_orientation" -> {
				try {
					val type = call.argument<String>("type") ?: "Portrait"
					val isReverse = call.argument<Boolean>("reverse") ?: false
					activity?.let {
						if (type == "Portrait") {
							if (isReverse) {
								it.requestedOrientation = SCREEN_ORIENTATION_REVERSE_PORTRAIT
							} else {
								it.requestedOrientation = SCREEN_ORIENTATION_UNSPECIFIED
							}
						} else if (type == "Landscape") {
							if (isReverse) {
								it.requestedOrientation = SCREEN_ORIENTATION_REVERSE_LANDSCAPE
							} else {
								it.requestedOrientation = SCREEN_ORIENTATION_LANDSCAPE
							}

						}

					}

					result.success("")
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"check_orientation" -> {
				try {
					val res = checkOrientation(context)
					result.success(res)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"gen_video_thumbnail_list" -> {
				try {
					val pathList = call.argument<List<String>>("path_list") ?: listOf()
					val outDirPath = call.argument<String>("out_dir_path") ?: ""
					ThumbnailUtil.genVideoThumbnail(
						outDirPath = outDirPath,
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

			"check_req_package_install_permission" -> {
				try {
					activity?.let {
						checkCanRequestPackageInstallsPermission(it)
						result.success("")
					}

				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"check_storage_permission_granted" -> {
				try {
					activity?.let {
						val res = isStoragePermissionGranted(it)
						result.success(res)
					}

				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"req_storage_permission" -> {
				try {
					activity?.let {
						requestStoragePermission(it)
						result.success("")
					}

				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"toggle_keep_screen" -> {
				try {
					val isKeep = call.argument<Boolean>("is_keep") ?: false
					activity?.let { toggleKeepScreenOn(it.window, isKeep) }
					result.success("")
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"get_device_id" -> {
				try {
					val androidId = Settings.Secure.getString(
						context.contentResolver,
						Settings.Secure.ANDROID_ID
					)
					result.success(androidId)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"get_local_ip_address" -> {
				try {
					val res = getLocalIpAddress()
					result.success(res)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"get_wifi_address" -> {
				try {
					val res = getWifiAddress()
					result.success(res)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"get_wifi_address_list" -> {
				try {
					val res = getWifiAddressList()
					result.success(res)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"open_url" -> {
				try {
					val url = call.argument<String>("url") ?: ""

					val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
					i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

					context.startActivity(i)
					result.success(true)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"hide_fullscreen" -> {
				activity?.let { hideFullScreen(it.window) }
				result.success(true)
			}

			"show_fullscreen" -> {
				activity?.let { showFullScreen(it.window) }
				result.success(true)
			}

			"genPdfCover" -> {
				val outDirPath = call.argument<String>("out_dir_path")
				val pdfListPath = call.argument<List<String>>("pdf_path_list")
				val size = call.argument<Int>("size") ?: 300

				if (outDirPath == null || pdfListPath == null) {
					result.error(
						"ERROR",
						"outDirPath == null || pdfListPath == null",
						"val outDirPath = call.argument<String>(\"\")\n" +
								"        val pdfListPath = call.argument<List<String>>(\"\")\n" +
								"        val size = call.argument<Int>(\"\")"
					)
					return
				}

				genPdfCoverList(
					pathList = pdfListPath,
					outPath = outDirPath,
					size = size,
					onLoaded = {
						result.success("success")
					}, onError = { err ->
						result.error("ERROR", err.toString(), err)
					})

			}

			"get_android_device_info" -> {
				try {
					val obj = mapOf(
						"fingerprint" to Build.FINGERPRINT,
						"soc_model" to Build.SOC_MODEL,
						"model" to Build.MODEL,
						"product" to Build.PRODUCT,
						"manufacture" to Build.MANUFACTURER,
						"hardware" to Build.HARDWARE,
						"bootloader" to Build.BOOTLOADER,
						"board" to Build.BOARD,
						"release_or_codename" to Build.VERSION.RELEASE_OR_CODENAME,
						"security_patch" to Build.VERSION.SECURITY_PATCH,
						"preview_sdk_int" to Build.VERSION.PREVIEW_SDK_INT,
						"sdk_int" to Build.VERSION.SDK_INT,
						"base_os" to Build.VERSION.BASE_OS,
						"codename" to Build.VERSION.CODENAME,
					)
					result.success(obj)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"getPlatformVersion" -> {
				result.success(android.os.Build.VERSION.RELEASE)
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
