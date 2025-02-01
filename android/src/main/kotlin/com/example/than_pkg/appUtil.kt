package com.example.than_pkg

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager

fun toggleFullscreen(context: Context, isFullscreen: Boolean) {
	if (context is Activity) {
		val window = context.window

		window.decorView.apply {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
				val controller = window.insetsController
				if (isFullscreen) {
					controller?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
					controller?.systemBarsBehavior =
						WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
				} else {
					controller?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
				}
			} else {
				systemUiVisibility = if (isFullscreen) {
					View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
							View.SYSTEM_UI_FLAG_FULLSCREEN or
							View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				} else {
					View.SYSTEM_UI_FLAG_VISIBLE
				}
			}
		}
	}
}

@SuppressLint("InlinedApi")
fun showFullScreen(context: Context) {
	if (context is Activity) {
		val window: Window = context.window

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


}

fun hideFullScreen(context: Context) {
	if (context is Activity) {
		val window: Window = context.window
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

}