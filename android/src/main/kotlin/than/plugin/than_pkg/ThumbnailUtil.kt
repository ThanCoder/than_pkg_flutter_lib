package than.plugin.than_pkg


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.os.Handler
import android.os.Looper
import android.text.TextUtils.split
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.concurrent.Executors

object ThumbnailUtil {

	private val handler = Handler(Looper.getMainLooper())
	private val executorService = Executors.newFixedThreadPool(3)


	fun genVideoThumbnail(pathList: List<String>,outDirPath:String, onCreated:()-> Unit,onError:(err: Exception)-> Unit) {
		executorService.execute {
			try {
				for (path in pathList) {
					val pngFile = File(path)
					val outPath = "$outDirPath/${pngFile.name.split(".").first()}.png"
					//already exists continue
					if(File(outPath).exists()) continue

					val retriever = MediaMetadataRetriever()
					retriever.setDataSource(path)

					val bitmap = retriever.getFrameAtTime(getRandomTime(path), MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
					retriever.release()

					bitmap?.let {

						FileOutputStream(outPath).use { out ->
							it.compress(Bitmap.CompressFormat.PNG, 100, out)
							out.flush()
						}
					}
				}

				handler.post { onCreated() }
			} catch (e: Exception) {
				handler.post { onError(e) }
			}
		}
	}

	fun genVideoThumbnail(videoPath: String, outPath: String,  onCreated:(imagePath: String)-> Unit,onError:(err: Exception)-> Unit) {
		executorService.execute {
			try {
				val retriever = MediaMetadataRetriever()
				retriever.setDataSource(videoPath)

				val bitmap = retriever.getFrameAtTime(getRandomTime(videoPath), MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
				retriever.release()

				bitmap?.let {
					FileOutputStream(outPath).use { out ->
						it.compress(Bitmap.CompressFormat.PNG, 100, out)
						out.flush()
					}
				}

				handler.post { onCreated(outPath) }
			} catch (e: Exception) {
				handler.post { onError(e) }
			}
		}
	}

	@Throws(Exception::class)
	fun genVideoThumbnailBitmap(videoPath: String, outPath: String): Bitmap? {
		val outFile = File(outPath)
		return if (outFile.exists()) {
			BitmapFactory.decodeFile(outPath)
		} else {
			val retriever = MediaMetadataRetriever()
			retriever.setDataSource(videoPath)

			val bitmap = retriever.getFrameAtTime(getRandomTime(videoPath), MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
			retriever.release()

			bitmap?.let {
				FileOutputStream(outPath).use { out ->
					it.compress(Bitmap.CompressFormat.PNG, 100, out)
					out.flush()
				}
			}
			bitmap
		}
	}

	@Throws(Exception::class)
	fun genVideoThumbnailBitmap(videoPath: String): Bitmap? {
		val retriever = MediaMetadataRetriever()
		retriever.setDataSource(videoPath)

		val bitmap = retriever.getFrameAtTime(getRandomTime(videoPath), MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
		retriever.release()

		return bitmap
	}

	@Throws(Exception::class)
	private fun getRandomTime(videoPath: String): Long {
		val retriever = MediaMetadataRetriever()
		retriever.setDataSource(videoPath)

		val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
		val duration = durationStr?.toLong() ?: 0L

		val randomTime = Random().nextInt(duration.toInt()).toLong()
		return randomTime * 1000 // Convert to microseconds
	}
}
