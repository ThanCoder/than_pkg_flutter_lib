package com.example.than_pkg

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import thancoder.pkg.thancoder_pkg.genPdfCoverList

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


	@SuppressLint("HardwareIds", "NewApi")
	override fun onMethodCall(call: MethodCall, result: Result) {
		when (call.method) {

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
			"fingerprint" -> {
				val res = Build.FINGERPRINT
				result.success(res)
			}
			"soc_model" -> {
				val res = Build.SOC_MODEL
				result.success(res)
			}
			"model" -> {
				val res = Build.MODEL
				result.success(res)
			}
			"product" -> {
				val res = Build.PRODUCT
				result.success(res)
			}
			"manufacture" -> {
				val res = Build.MANUFACTURER
				result.success(res)
			}
			"hardware" -> {
				val res = Build.HARDWARE
				result.success(res)
			}
			"brand" -> {
				val res = Build.BRAND
				result.success(res)
			}
			"bootloader" -> {
				val res = Build.BOOTLOADER
				result.success(res)
			}
			"board" -> {
				val res = Build.BOARD
				result.success(res)
			}
			"release_or_codename" -> {
				val res = Build.VERSION.RELEASE_OR_CODENAME
				result.success(res)
			}
			"security_patch" -> {
				val res = Build.VERSION.SECURITY_PATCH
				result.success(res)
			}
			"preview_sdk_int" -> {
				val res = Build.VERSION.PREVIEW_SDK_INT
				result.success(res)
			}
			"sdk_int" -> {
				val res = Build.VERSION.SDK_INT
				result.success(res)
			}

			"base_os" -> {
				val res = Build.VERSION.BASE_OS
				result.success(res)
			}

			"codename" -> {
				val res = Build.VERSION.CODENAME
				result.success(res)
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
