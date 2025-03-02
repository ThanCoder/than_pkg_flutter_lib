package than.plugin.than_pkg

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Handler
import android.os.Looper
import android.os.ParcelFileDescriptor
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executors

object PdfUtil {
	private val handler = Handler(Looper.getMainLooper())
	private val executorService = Executors.newSingleThreadExecutor();

	fun getPdfPageCount(
		pdfPath: String,
		onLoaded: (pageCount: Int) -> Unit,
		onError: (Exception) -> Unit,
	) {
		executorService.execute {
			try {
				// PDF ဖိုင်ဖွင့်ခြင်း
				val pdfFile = File(pdfPath)
				val fileDescriptor =
					ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)

				// PdfRenderer ဖန်တီးခြင်း
				val pdfRenderer = PdfRenderer(fileDescriptor)
				handler.post { onLoaded(pdfRenderer.pageCount) }

			} catch (e: Exception) {
				handler.post { onError(e) }
			}
		}

	}

	fun genPdfThumbnail(
		pdfPath: String,
		thumbnailPath: String,
		size: Int = -1,
		pageIndex: Int = 0,
		isExistsSkip: Boolean = true,
		onLoaded: (thumbnailPath: String) -> Unit,
		onError: (Exception) -> Unit,
	) {
		executorService.execute {
			try {
				val thumbnailFile = File(thumbnailPath)
				if (isExistsSkip && thumbnailFile.exists()) {
					handler.post { onLoaded(thumbnailPath) }
					return@execute
				}

				// PDF ဖိုင်ဖွင့်ခြင်း
				val pdfFile = File(pdfPath)
				val fileDescriptor =
					ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)

				// PdfRenderer ဖန်တီးခြင်း
				val pdfRenderer = PdfRenderer(fileDescriptor)

				// ပထမဆုံး Page ကိုရယူခြင်း
				val page = pdfRenderer.openPage(pageIndex)
				var width: Int = size
				var height: Int = size

				if (size == -1) {
					width = page.width
					height = page.height
				}

				// Bitmap ဖန်တီးခြင်း (PDF page အရွယ်အစား)
				val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

				// PDF page ကို render လုပ်ခြင်း
				page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

				// Page ကိုပိတ်ခြင်း
				page.close()
				pdfRenderer.close()

				// PNG ဖိုင်အဖြစ် Save
				FileOutputStream(thumbnailFile).use { outputStream ->
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
					outputStream.flush()
				}
				handler.post { onLoaded(thumbnailPath) }
			} catch (e: Exception) {
				handler.post { onError(e) }
			}
		}
	}

	fun genPdfCoverList(
		pathList: List<String>,
		outPath: String,
		size: Int = 300,
		onLoaded: () -> Unit,
		onError: (Exception) -> Unit,
	) {
		executorService.execute {
			if (outPath.isEmpty()) {
				handler.post { onError(Exception("outPath is Empty")) }
				return@execute
			}

			val outDir = File(outPath)
			if (!outDir.exists()) {
				handler.post { onError(Exception("outPath does not exist")) }

				return@execute
			}

			try {
				for (path in pathList) {
					try {
						// PDF ဖိုင်ဖွင့်ခြင်း
						val pdfFile = File(path)
						val fileDescriptor =
							ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)

						// PdfRenderer ဖန်တီးခြင်း
						val pdfRenderer = PdfRenderer(fileDescriptor)

						// ပထမဆုံး Page ကိုရယူခြင်း
						val page = pdfRenderer.openPage(0)

						// Bitmap ဖန်တီးခြင်း (PDF page အရွယ်အစား)
						val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)

						// PDF page ကို render လုပ်ခြင်း
						page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

						// Page ကိုပိတ်ခြင်း
						page.close()
						pdfRenderer.close()

						// PNG ဖိုင်အဖြစ် Save
						val pngFile = File(outDir, "${pdfFile.name.split(".").first()}.png")
						FileOutputStream(pngFile).use { outputStream ->
							bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
							outputStream.flush()
						}

					} catch (e: Exception) {
						e.printStackTrace()
					}
				}

				// UI Thread မှာ callback ပြန်ပေးခြင်း
				handler.post { onLoaded() }

			} catch (e: Exception) {
				handler.post { onError(e) }
			}
		}

	}
}
