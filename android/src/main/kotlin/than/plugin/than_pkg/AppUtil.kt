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


object AppUtil {
	fun showToast(ctx: Context, msg: String, toastDuraction: Int = Toast.LENGTH_SHORT) {
		Toast.makeText(ctx, msg, toastDuraction).show()
	}

	fun getBatteryLevel(context: Context): Int {
		val batteryManager =
			context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
		val batteryLevel =
			batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
		return batteryLevel
	}

	fun isDarkModeEnabled(context: Context): Boolean {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // Android 10 and above
			val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
			return uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES
		} else {
			return false // Before Android 10, Dark Mode is not available
		}
	}

	fun openPdfWithIntent(filePath: String, activity: Activity) {
		TContentProvider.openPdfFile(activity, filePath)
	}

	fun openVideoWithIntent(filePath: String, activity: Activity) {
		TContentProvider.openVideoFile(activity, filePath)
	}

	fun installApk(filePath: String, activity: Activity) {
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

	fun openUrl(context: Context, url: String) {
		val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
		i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

		context.startActivity(i)
	}

	@SuppressLint("HardwareIds")
	fun getDeviceId(context: Context): String {
		val androidId = Settings.Secure.getString(
			context.contentResolver,
			Settings.Secure.ANDROID_ID
		)
		return androidId
	}

	// Check the current screen orientation (Portrait or Landscape)
	fun checkOrientation(context: Context): String {
		val orientation = context.resources.configuration.orientation
		return if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			"Portrait"
		} else {
			"Landscape"
		}
	}

	fun requestOrientation(ctx: Activity, type: String, isReverse: Boolean = false) {
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

	fun toggleKeepScreenOn(window: Window, enable: Boolean) {
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

	fun hideFullScreen(window: Window, ctx: Context) {
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

	fun isFullScreen(window: android.view.Window): Boolean {
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			// ✅ Android 11+ (API 30+)
			val insets = window.decorView.rootWindowInsets
			val isFullscreen = insets?.isVisible(WindowInsets.Type.statusBars()) == false
			isFullscreen
		} else {
			// ✅ Android 10 and below (API 29 and below)
			val flags = window.decorView.systemUiVisibility
			val fullscreenFlags =
				View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
			(flags and fullscreenFlags) == fullscreenFlags
		}
	}
}