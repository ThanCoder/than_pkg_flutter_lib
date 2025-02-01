package com.example.than_pkg

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import thancoder.pkg.thancoder_pkg.genPdfCoverList

/** ThanPkgPlugin */
class ThanPkgPlugin : FlutterPlugin, MethodCallHandler {
	/// The MethodChannel that will the communication between Flutter and native Android
	///
	/// This local reference serves to register the plugin with the Flutter Engine and unregister it
	/// when the Flutter Engine is detached from the Activity
	private lateinit var channel: MethodChannel
	private lateinit var context: Context

	override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
		channel = MethodChannel(flutterPluginBinding.binaryMessenger, "than_pkg")
		channel.setMethodCallHandler(this)
		context = flutterPluginBinding.applicationContext

	}

	override fun onMethodCall(call: MethodCall, result: Result) {
		when (call.method) {
			"getPlatformVersion" -> {
				result.success(android.os.Build.VERSION.RELEASE)
			}
			"get_local_ip_address" ->{
				try {
					val res = getLocalIpAddress()
					result.success(res)
				}catch (err:Exception){
					result.error("ERROR", err.toString(), err)
				}
			}
			"get_wifi_address" ->{
				try {
					val res = getWifiAddress()
					result.success(res)
				}catch (err:Exception){
					result.error("ERROR", err.toString(), err)
				}
			}
			"get_wifi_address_list" ->{
				try {
					val res = getWifiAddressList()
					result.success(res)
				}catch (err:Exception){
					result.error("ERROR", err.toString(), err)
				}
			}
			"open_url" ->{
				try {
					val url = call.argument<String>("url") ?: ""

					val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
					i.flags =Intent.FLAG_ACTIVITY_NEW_TASK

					context.startActivity(i)
					result.success(true)
				}catch (err:Exception){
					result.error("ERROR", err.toString(), err)
				}
			}
			"hide_fullscreen" -> {
				hideFullScreen(context)
//				toggleFullscreen(context,false)
				result.success(true)
			}
			"show_fullscreen" -> {
				showFullScreen(context)
//				toggleFullscreen(context,true)
				result.success(true)
			}
			"genPdfCover" -> {
				val outDirPath = call.argument<String>("out_dir_path")
				val pdfListPath = call.argument<List<String>>("pdf_path_list")
				val size = call.argument<Int>("size") ?: 300

				if (outDirPath == null || pdfListPath == null) {
					result.error(
						"ERROR",
						"outDirPath == null || pdfListPath == null",
						"val outDirPath = call.argument<String>(\"\")\n" +
								"        val pdfListPath = call.argument<List<String>>(\"\")\n" +
								"        val size = call.argument<Int>(\"\")"
					)
					return
				}

				genPdfCoverList(
					pathList = pdfListPath,
					outPath = outDirPath,
					size = size,
					onLoaded = {
						result.success("success")
					}, onError = { err ->
						result.error("ERROR", err.toString(), err)
					})

			}

			else -> {
				result.notImplemented()
			}
		}
	}

	override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
		channel.setMethodCallHandler(null)
	}
}
