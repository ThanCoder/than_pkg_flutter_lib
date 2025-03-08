package than.plugin.than_pkg

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import androidx.core.app.ActivityCompat.startActivityForResult
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.Result

object CameraUtil {
	fun callCheck(call: MethodCall, result: Result, context: Context, activity: Activity?) {
		val method = call.method.replace("cameraUtil/", "")
		when (method) {
//camera
			"openCamera" -> {
				activity?.let {
					openCamera(it)
				}
				result.success("")
			}
		}
	}

	fun openCamera(activity: Activity) {
		val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
		activity.startActivityForResult(cameraIntent, 20000)
	}
}