package than.plugin.than_pkg

import android.app.Activity
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.provider.Settings
import java.io.File
import java.io.FileNotFoundException

class TContentProvider : ContentProvider() {

	/*
	 <application>
		<provider
            android:name=".TContentProvider"
            android:authorities="${applicationId}.provider"
            android:exported="false"
            android:grantUriPermissions="true" />

	 </application>
	*/

	companion object {
		private const val REQUEST_CODE = 1234
		var filePath: String = ""
		private var type: String? = null
		private var currentUri:Uri? = null

		@JvmStatic
		@Throws(Exception::class)
		fun installApk(ctx: Activity, apkPath: String) {
			filePath = apkPath
			type = "application/vnd.android.package-archive"
			currentUri = Uri.parse("content://${ctx.packageName}.provider/${File(filePath).name}")
			val installIntent = Intent(Intent.ACTION_VIEW).apply {
				setDataAndType(currentUri, "application/vnd.android.package-archive")
				flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
			}
			ctx.startActivity(installIntent)
		}

		@JvmStatic
		@Throws(Exception::class)
		fun openVideoFile(ctx: Activity, videoPath: String) {
			openVideoFile(ctx, videoPath, File(videoPath).name)
		}

		@JvmStatic
		@Throws(Exception::class)
		fun openVideoFile(ctx: Activity, videoPath: String, title: String) {
			filePath = videoPath
			type = "video/mp4"
			currentUri = Uri.parse("content://${ctx.packageName}.provider/${File(filePath).name}")
			val intent = Intent(Intent.ACTION_VIEW).apply {
				setDataAndType(currentUri, "video/mp4")
				flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
			}
			ctx.startActivity(intent)
		}

		@JvmStatic
		@Throws(Exception::class)
		fun openPdfFile(ctx: Activity, path: String) {
			openPdfFile(ctx, path, File(path).name)
		}

		@JvmStatic
		@Throws(Exception::class)
		fun openPdfFile(ctx: Activity, path: String, title: String) {
			filePath = path
			type = "application/pdf"
			currentUri = Uri.parse("content://${ctx.packageName}.provider/${File(filePath).name}")
			val intent = Intent(Intent.ACTION_VIEW).apply {
				setDataAndType(currentUri, "application/pdf")
				flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
			}
			ctx.startActivity(Intent.createChooser(intent, "Open PDF"))
		}

		@JvmStatic
		@Throws(Exception::class)
		fun checkPermission(ctx: Activity): Boolean {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				if (!ctx.packageManager.canRequestPackageInstalls()) {
					val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).apply {
						data = Uri.parse("package:${ctx.packageName}")
					}
					ctx.startActivityForResult(intent, REQUEST_CODE)
					return false
				}
			}
			return true
		}
	}

	override fun onCreate(): Boolean = false

	@Throws(FileNotFoundException::class)
	override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor? {
		val file = File(filePath)
		if (!file.exists()) throw FileNotFoundException("File not found!")
		return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
	}

	override fun query(
		uri: Uri, projection: Array<String>?, selection: String?,
		selectionArgs: Array<String>?, sortOrder: String?
	): Cursor? = null

	override fun getType(uri: Uri): String? = type

	override fun insert(uri: Uri, values: ContentValues?): Uri? = currentUri

	override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

	override fun update(
		uri: Uri,
		values: ContentValues?,
		selection: String?,
		selectionArgs: Array<String>?
	): Int = 0
}
