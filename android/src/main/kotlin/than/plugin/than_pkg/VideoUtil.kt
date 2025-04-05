package than.plugin.than_pkg


import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.os.Handler
import android.os.Looper
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.Result
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.concurrent.Executors

object VideoUtil {

	fun callCheck(call: MethodCall, result: Result, context: Context, activity: Activity?) {
		val method = call.method.replace("videoUtil/", "")
		when (method) {
			//new methods
			"genVideoThumbnail2" -> {
				try {
					val rawList = call.argument<List<Any>>("path_list")
					val isOverride = call.argument<Boolean>("is_override") ?: false
					val size = call.argument<Int>("size") ?: 300
					val pathList = rawList?.mapNotNull { item ->
						(item as? Map<*, *>)
					}

					if (pathList != null) {
						genVideoThumbnail2(pathList = pathList,
							isOverride = isOverride,
							size = size,
							onCreated = {
								result.success("")
							},
							onError = { err ->
								result.error("ERROR", err.toString(), err)
							})
					}
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}
			//video thumbnail
			"genVideoThumbnailList" -> {
				try {
					val pathList = call.argument<List<String>>("path_list") ?: listOf()
					val outDirPath = call.argument<String>("out_dir_path") ?: ""

					genVideoThumbnailList(outDirPath = outDirPath,
						pathList = pathList,
						onCreated = {
							result.success("")
						},
						onError = { err ->
							result.error("ERROR", err.toString(), err)
						})
				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}

			"genVideoThumbnail" -> {
				try {
					val videoPath = call.argument<String>("video_path") ?: ""
					val outPath = call.argument<String>("out_path") ?: ""

					genVideoThumbnail(videoPath = videoPath,
						outPath = outPath,
						onCreated = { savedPath ->
							result.success(savedPath)
						},
						onError = { err ->
							result.error("ERROR", err.toString(), err)
						})


				} catch (err: Exception) {
					result.error("ERROR", err.toString(), err)
				}
			}
		}
	}

	private val handler = Handler(Looper.getMainLooper())
	private val executorService = Executors.newSingleThreadExecutor()


	private fun genVideoThumbnail2(
		pathList: List<Map<*, *>>,
		size: Int = 300,
		isOverride: Boolean = false,
		onCreated: () -> Unit,
		onError: (err: Exception) -> Unit
	) {
		executorService.execute {
			try {
				for (path in pathList) {
					try {
						if (!isOverride && File(path["dist"].toString()).exists()) continue

						val pngFile = File(path["dist"].toString())

						val retriever = MediaMetadataRetriever()
						retriever.setDataSource(path["src"].toString())

						val bitmap = retriever.getFrameAtTime(
							getRandomTime(path["src"].toString()),
							MediaMetadataRetriever.OPTION_CLOSEST_SYNC
						)
						retriever.release()

						bitmap?.let {

							FileOutputStream(pngFile.path).use { out ->
								it.compress(Bitmap.CompressFormat.PNG, 100, out)
								out.flush()
							}
						}
					} catch (_: Exception) {
					}
				}

				handler.post { onCreated() }
			} catch (e: Exception) {
				handler.post { onError(e) }
			}
		}
	}


	private fun genVideoThumbnailList(
		pathList: List<String>,
		outDirPath: String,
		onCreated: () -> Unit,
		onError: (err: Exception) -> Unit
	) {
		executorService.execute {
			try {
				for (path in pathList) {
					try {
						val pngFile = File(path)
						val outPath = "$outDirPath/${pngFile.name.split(".").first()}.png"
						//already exists continue
						if (File(outPath).exists()) continue

						val retriever = MediaMetadataRetriever()
						retriever.setDataSource(path)

						val bitmap = retriever.getFrameAtTime(
							getRandomTime(path),
							MediaMetadataRetriever.OPTION_CLOSEST_SYNC
						)
						retriever.release()

						bitmap?.let {

							FileOutputStream(outPath).use { out ->
								it.compress(Bitmap.CompressFormat.PNG, 100, out)
								out.flush()
							}
						}
					} catch (_: Exception) {
					}
				}

				handler.post { onCreated() }
			} catch (e: Exception) {
				handler.post { onError(e) }
			}
		}
	}

	private fun genVideoThumbnail(
		videoPath: String,
		outPath: String,
		onCreated: (imagePath: String) -> Unit,
		onError: (err: Exception) -> Unit
	) {
		executorService.execute {
			try {
				val retriever = MediaMetadataRetriever()
				retriever.setDataSource(videoPath)

				val bitmap = retriever.getFrameAtTime(
					getRandomTime(videoPath),
					MediaMetadataRetriever.OPTION_CLOSEST_SYNC
				)
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
	private fun getRandomTime(videoPath: String): Long {
		var res: Long = 1000000
		try {
			val retriever = MediaMetadataRetriever()
			retriever.setDataSource(videoPath)

			val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
			val duration = durationStr?.toLong() ?: 0L

			val randomTime = Random().nextInt(duration.toInt()).toLong()
			res = randomTime * 1000 // Convert to microseconds
		} catch (_: Exception) {
		}
		return res
	}
}
