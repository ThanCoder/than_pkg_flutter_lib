package than.plugin.than_pkg

import android.content.Context
import android.graphics.Bitmap
import java.io.File

object FileUtil {
	// Bitmap ကို File အဖြစ်သိမ်းပေးမယ်
	fun saveBitmapToFile(context: Context, bitmap: Bitmap): String {
		val file = File(context.cacheDir, "captured_image_${System.currentTimeMillis()}.jpg")
		try{
			file.outputStream().use { out ->
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
			out.flush()
		}
		}catch(e:Exception){}
		return file.absolutePath
	}
}