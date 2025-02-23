package than.plugin.than_pkg

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import java.io.File
import java.io.FileOutputStream

fun genPdfCoverList(
	pathList: List<String>,
	outPath: String,
	size:Int=300,
	onLoaded:() -> Unit,
	onError:(Exception) -> Unit,
) {
	if (outPath.isEmpty()) {
		onError(Exception("outPath is Empty"))
		return
	}

	val outDir = File(outPath)
	if (!outDir.exists()) {
		onError(Exception("outPath does not exist"))
		return
	}

	try {
		for (path in pathList) {
			try {
				// PDF ဖိုင်ဖွင့်ခြင်း
				val pdfFile = File(path)
				val fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)

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
		onLoaded()

	} catch (e: Exception) {
		onError(e)
	}
}
