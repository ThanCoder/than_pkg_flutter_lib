package than.plugin.than_pkg

import android.annotation.SuppressLint
import android.app.Activity
import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.Uri
import android.os.BatteryManager
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.than_pkg.R
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.Result


object AppUtil {
	@SuppressLint("MissingPermission", "NewApi")
	fun callCheck(call: MethodCall, result: Result, context: Context, activity: Activity?) {
		val method = call.method.replace("appUtil/", "")
		when (method) {
			"openUrl" -> {
				try {
					val url = call.argument<String>("url") ?: ""
					openUrl(context, url)
					result.success(true)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"hideFullScreen" -> {
				activity?.let {
					hideFullScreen(it.window, context)
				}
				result.success(true)
			}

			"showFullScreen" -> {
				activity?.let { showFullScreen(it.window) }
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
					val res = getBatteryLevel(context)
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
					@Suppress("DEPRECATION") result.success(networkInfo?.isConnected == true)

				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"isDarkModeEnabled" -> {
				try {
					val isDarkMode = isDarkModeEnabled(context)
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
						requestOrientation(it, isReverse = isReverse, type = type)
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
					val obj = getDeviceInfo()
					result.success(obj)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"getPlatformVersion" -> {
				result.success(Build.VERSION.RELEASE)
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
					val androidId = getDeviceId(context)
					result.success(androidId)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"showToast" -> {
				try {
					val msg = call.argument<String>("message") ?: ""
					showToast(context, msg)
					result.success("")
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"openPdfWithIntent" -> {
				try {
					val path = call.argument<String>("path") ?: ""
					activity?.let { it -> openPdfWithIntent(path, it) }
					result.success("")
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"openVideoWithIntent" -> {
				try {
					val path = call.argument<String>("path") ?: ""
					activity?.let { it -> openVideoWithIntent(path, it) }
					result.success("")
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"installApk" -> {
				try {
					val path = call.argument<String>("path") ?: ""
					activity?.let { it -> installApk(path, it) }
					result.success("")
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"isFullScreen" -> {
				try {
					activity?.let {
						val res = isFullScreen(window = activity.window)
						result.success(res)
						return@let
					}
					result.success(false)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}
			"getSdkInt" -> {
				try {
					result.success(Build.VERSION.SDK_INT)
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}
		}
	}

	private fun showToast(ctx: Context, msg: String, toastDuration: Int = Toast.LENGTH_SHORT) {
		Toast.makeText(ctx, msg, toastDuration).show()
	}

	private fun getBatteryLevel(context: Context): Int {
		val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
		val batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
		return batteryLevel
	}

	private fun isDarkModeEnabled(context: Context): Boolean {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // Android 10 and above
			val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
			return uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES
		} else {
			return false // Before Android 10, Dark Mode is not available
		}
	}

	private fun openPdfWithIntent(filePath: String, activity: Activity) {
		TContentProvider.openPdfFile(activity, filePath)
	}

	private fun openVideoWithIntent(filePath: String, activity: Activity) {
		TContentProvider.openVideoFile(activity, filePath)
	}

	private fun installApk(filePath: String, activity: Activity) {
		TContentProvider.installApk(activity, filePath)
	}

	@RequiresApi(Build.VERSION_CODES.S)
	fun getDeviceInfo(): Map<String, Any> {
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
		return obj
	}

	private fun openUrl(context: Context, url: String) {
		val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
		i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

		context.startActivity(i)
	}

	@SuppressLint("HardwareIds")
	fun getDeviceId(context: Context): String {
		val androidId = Settings.Secure.getString(
			context.contentResolver, Settings.Secure.ANDROID_ID
		)
		return androidId
	}

	// Check the current screen orientation (Portrait or Landscape)
	private fun checkOrientation(context: Context): String {
		val orientation = context.resources.configuration.orientation
		return if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			"Portrait"
		} else {
			"Landscape"
		}
	}

	private fun requestOrientation(ctx: Activity, type: String, isReverse: Boolean = false) {
		if (type == "Portrait") {
			if (isReverse) {
				ctx.requestedOrientation = SCREEN_ORIENTATION_UNSPECIFIED
			} else {
				ctx.requestedOrientation = SCREEN_ORIENTATION_UNSPECIFIED
			}
		} else if (type == "Landscape") {
			if (isReverse) {
				ctx.requestedOrientation = SCREEN_ORIENTATION_REVERSE_LANDSCAPE
			} else {
				ctx.requestedOrientation = SCREEN_ORIENTATION_LANDSCAPE
			}

		}
	}

	private fun toggleKeepScreenOn(window: Window, enable: Boolean) {
		if (enable) {
			window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
		} else {
			window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
		}

	}

	@SuppressLint("InlinedApi")
	fun showFullScreen(window: Window) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
			// ✅ Notch Support (Android 9+)
			val layoutParams = window.attributes
			layoutParams.layoutInDisplayCutoutMode =
				WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
			window.attributes = layoutParams
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			// ✅ Android 11+ (API 30+)
			window.setDecorFitsSystemWindows(false)
			val controller = window.insetsController
			controller?.let {
				it.hide(WindowInsets.Type.systemBars())
				it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
			}
		} else {
			// ✅ Android 10 and below
			val decorView: View = window.decorView
			decorView.systemUiVisibility =
				(View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
		}

	}

	private fun hideFullScreen(window: Window, ctx: Context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			// ✅ Android 11+ (API 30+)
			window.setDecorFitsSystemWindows(true)
			val controller = window.insetsController
			controller?.show(WindowInsets.Type.systemBars())
		} else {
			// ✅ Android versions below API 30 (Android 10 and below)
			val decorView: View = window.decorView
			decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE


		}
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
			val decorView: View = window.decorView
			decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

			// ✅ Status Bar & Navigation Bar Color ပြန်သတ်မှတ်ပါ
			window.statusBarColor = 0xFF000000.toInt() // သင့် UI Design အလိုက် ပြောင်းပါ
			window.navigationBarColor = 0xFF000000.toInt()// သင့် UI Design အလိုက် ပြောင်းပါ
		}

//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//			val controller = window.insetsController
//			controller?.setSystemBarsAppearance(
//				WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
//				WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
//			)
//		}

	}

	private fun isFullScreen(window: Window): Boolean {
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			// ✅ Android 11+ (API 30+)
			val insets = window.decorView.rootWindowInsets
			val isFullscreen = insets?.isVisible(WindowInsets.Type.statusBars()) == false
			isFullscreen
		} else {
			// ✅ Android 10 and below (API 29 and below)
			val flags = window.decorView.systemUiVisibility
			val fullscreenFlags = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
			(flags and fullscreenFlags) == fullscreenFlags
		}
	}
}