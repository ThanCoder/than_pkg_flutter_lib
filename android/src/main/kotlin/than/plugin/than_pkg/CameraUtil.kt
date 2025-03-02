package than.plugin.than_pkg

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.core.app.ActivityCompat.startActivityForResult

object CameraUtil {
	fun openCamera(activity: Activity) {
		val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
		activity.startActivityForResult(cameraIntent, 20000)
	}
}