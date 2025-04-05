package than.plugin.than_pkg

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Handler
import android.os.Looper
import android.os.ParcelFileDescriptor
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.Result
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executors

object PdfUtil {
	fun callCheck(call: MethodCall, result: Result, context: Context, activity: Activity?) {
		val method = call.method.replace("pdfUtil/", "")
		when (method) {
			"genPdfThumbnail" -> {
				val rawList = call.argument<List<Any>>("path_list")
				val isOverride = call.argument<Boolean>("is_override")?: false
				val pathList = rawList?.mapNotNull { item ->
					(item as? Map<*, *>)
				}
				val size = call.argument<Int>("size") ?: 300

				if (pathList != null) {
					genPdfThumbnail2(pathList = pathList, isOverride = isOverride, size = size, onLoaded = {
						result.success("success")
					}, onError = { err ->
						result.error("ERROR", err.toString(), err)
					})
				}

			}
			//pdf util
			"genPdfCoverList" -> {
				val outDirPath = call.argument<String>("out_dir_path")
				val pdfListPath = call.argument<List<String>>("pdf_path_list")
				val size = call.argument<Int>("size") ?: 300

				if (outDirPath == null || pdfListPath == null) {
					result.error(
						"ERROR",
						"outDirPath == null || pdfListPath == null",
						"val outDirPath = call.argument<String>(\"\")\n" + "        val pdfListPath = call.argument<List<String>>(\"\")\n" + "        val size = call.argument<Int>(\"\")"
					)
					return
				}
				genPdfCoverList(pathList = pdfListPath, outPath = outDirPath, size = size, onLoaded = {
					result.success("success")
				}, onError = { err ->
					result.error("ERROR", err.toString(), err)
				})

			}

			"genPdfImage" -> {
				val pdfPath = call.argument<String>("pdf_path") ?: ""
				val outPath = call.argument<String>("out_path") ?: ""
				val pageIndex = call.argument<Int>("page_index") ?: 0
				val size = call.argument<Int>("size") ?: -1
				genPdfThumbnail(pdfPath = pdfPath,
					thumbnailPath = outPath,
					size = size,
					pageIndex = pageIndex,
					onLoaded = { savedPath ->
						result.success(savedPath)
					},
					onError = { err ->
						result.error("ERROR", err.toString(), err)
					})
			}

			"getPdfPageCount" -> {
				val pdfPath = call.argument<String>("pdf_path") ?: ""
				getPdfPageCount(pdfPath = pdfPath, onLoaded = { pageCount ->
					result.success(pageCount)
				}, onError = { err ->
					result.error("ERROR", err.toString(), err)
				})
			}
		}
	}

	private val handler = Handler(Looper.getMainLooper())
	private val executorService = Executors.newSingleThreadExecutor()

	private fun getPdfPageCount(
		pdfPath: String,
		onLoaded: (pageCount: Int) -> Unit,
		onError: (Exception) -> Unit,
	) {
		executorService.execute {
			try {
				// PDF ဖိုင်ဖွင့်ခြင်း
				val pdfFile = File(pdfPath)
				val fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)

				// PdfRenderer ဖန်တီးခြင်း
				val pdfRenderer = PdfRenderer(fileDescriptor)
				handler.post { onLoaded(pdfRenderer.pageCount) }

			} catch (e: Exception) {
				handler.post { onError(e) }
			}
		}

	}

	@SuppressLint("Range")
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
				val fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)

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

	private fun genPdfThumbnail2(
		pathList:  List<Map<*, *>>,
		size: Int = 300,
		isOverride: Boolean=false,
		onLoaded: () -> Unit,
		onError: (Exception) -> Unit,
	) {
		executorService.execute {
			try {
				for (path in pathList) {
					try {
						if(!isOverride && File(path["dist"].toString()).exists()) continue
						// PDF ဖိုင်ဖွင့်ခြင်း
						val pdfFile = File(path["src"].toString())
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
						val pngFile = File(path["dist"].toString())
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

	private fun genPdfCoverList(
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
