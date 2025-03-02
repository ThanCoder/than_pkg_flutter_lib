package than.plugin.than_pkg

import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Enumeration


object WifiUtil {
	// Function to get local IP address (checks for 192.168.x.x format)
	fun getLocalIpAddress(): String {
		return try {
			val interfaces: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
			while (interfaces.hasMoreElements()) {
				val networkInterface = interfaces.nextElement()

				// Skip inactive or loopback interfaces
				if (!networkInterface.isUp || networkInterface.isLoopback) continue

				val addresses: Enumeration<InetAddress> = networkInterface.inetAddresses
				while (addresses.hasMoreElements()) {
					val address = addresses.nextElement()
					val ip = address.hostAddress

					// Check for IPv4 and 192.168 prefix
					if (ip != null) {
						if (ip.startsWith("192.168")) {
							return ip
						}
					}
				}
			}
			"localhost"
		} catch (e: Exception) {
			e.printStackTrace()
			"localhost"
		}
	}


	// Function to get the WiFi address (IPv4 only)
	fun getWifiAddress(): String {
		var host = "localhost"
		try {
			val interfaces: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
			while (interfaces.hasMoreElements()) {
				val networkInterface = interfaces.nextElement()

				// Ignore loopback and inactive interfaces
				if (!networkInterface.isUp || networkInterface.isLoopback) continue

				val addresses: Enumeration<InetAddress> = networkInterface.inetAddresses
				while (addresses.hasMoreElements()) {
					val address = addresses.nextElement()

					// Check if it's an IPv4 address
					if (address.hostAddress?.contains(".") == true) {
//					println("Found IP Address: ${address.hostAddress}")
						host = address.hostAddress?.toString() ?: ""

					}
				}
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
		return host
	}

	fun getWifiAddressList(): List<String> {
		val list = mutableListOf<String>()
		try {
			val interfaces: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
			while (interfaces.hasMoreElements()) {
				val networkInterface = interfaces.nextElement()

				// Ignore loopback and inactive interfaces
				if (!networkInterface.isUp || networkInterface.isLoopback) continue

				val addresses: Enumeration<InetAddress> = networkInterface.inetAddresses
				while (addresses.hasMoreElements()) {
					val address = addresses.nextElement()

					// Check if it's an IPv4 address
					if (address.hostAddress?.contains(".") == true) {
//					println("Found IP Address: ${address.hostAddress}")
						val host = address.hostAddress?.toString() ?: ""
						list.add(host)
					}
				}
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
		return list;
	}

}