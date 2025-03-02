package than.plugin.than_pkg

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File


/*
AndroidManifest.xml >  <application> အဲထဲမှာထည့် <application/>
	<provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="${applicationId}.provider"
    android:grantUriPermissions="true"
    android:exported="false">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_paths" />
	</provider>

📌 Project ထဲမှာ res/xml/file_paths.xml ဖန်တီးပြီး

<?xml version="1.0" encoding="utf-8"?>
<paths>
<external-files-path name="pdfs" path="." />
</paths>

*/

object TProvider {
	fun openPdfWithIntent(filePath: String, context: android.content.Context) {
		val file = File(filePath)
		if (!file.exists()) {
			Log.e("PDF Open", "File not found: $filePath")
			return
		}

		val uri: Uri =
			// ✅ Android 7+ (API 24+) မှာ FileProvider သုံးရမယ်
			FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

		val intent = Intent(Intent.ACTION_VIEW).apply {
			setDataAndType(uri, "application/pdf")
			flags = Intent.FLAG_GRANT_READ_URI_PERMISSION // ✅ Read Permission ပေးမယ်
		}

		try {
			context.startActivity(intent) // ✅ PDF Viewer ဖြင့် ဖွင့်မယ်
		} catch (e: Exception) {
			Log.e("PDF Open", "No app found to open PDF", e)
		}
	}

	fun openVideoFile(context: Context, videoPath: String) {
		val videoFile = File(videoPath)

		// FileProvider URI ပြောင်းခြင်း
		val uri: Uri = FileProvider.getUriForFile(
			context,
			"${context.packageName}.provider",
			videoFile
		)

		val intent = Intent(Intent.ACTION_VIEW).apply {
			setDataAndType(uri, "video/mp4") // MP4 format
			addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Allow read permission
		}

		context.startActivity(Intent.createChooser(intent, "Open Video"))
	}

}