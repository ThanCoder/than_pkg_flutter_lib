package than.plugin.than_pkg

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager

// Check the current screen orientation (Portrait or Landscape)
fun checkOrientation(context:Context): String {
	val orientation = context.resources.configuration.orientation
	return if (orientation == Configuration.ORIENTATION_PORTRAIT) {
		"Portrait"
	} else {
		"Landscape"
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

	// ✅ Notch Devices: Ensure content fits all display cuts
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
		val layoutParams = window.attributes
		layoutParams.layoutInDisplayCutoutMode =
			WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS
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
		// ✅ Android versions below API 30 (Android 10 and below)
		val decorView: View = window.decorView
		decorView.systemUiVisibility =
			(View.SYSTEM_UI_FLAG_FULLSCREEN
					or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
	}


}

fun hideFullScreen(window: Window) {
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


}