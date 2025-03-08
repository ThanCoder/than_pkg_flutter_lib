package than.plugin.than_pkg

import android.app.Activity
import android.content.Context
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.Result


//<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
//<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>

object LocationUtil {
	fun callCheck(call: MethodCall, result: Result, context: Context, activity: Activity?) {
		val method = call.method.replace("locationUtil/", "")
		when (method) {

		}
	}

//	private fun getLastLocation() {
//		fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//		fusedLocationClient.lastLocation
//			.addOnSuccessListener(this, OnSuccessListener<Location> { location ->
//				// Got last known location
//				location?.let {
//					val latitude = it.latitude
//					val longitude = it.longitude
//					// Use the location data
//					println("Latitude: $latitude, Longitude: $longitude")
//				}
//			})
//	}
}