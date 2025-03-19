import 'package:flutter/services.dart';

class AndroidWifiUtil {
  static final AndroidWifiUtil wifi = AndroidWifiUtil._();
  AndroidWifiUtil._();
  factory AndroidWifiUtil() => wifi;

  final _channel = const MethodChannel('than_pkg');
  final _name = 'wifiUtil';

  Future<String> getWifiSSID() async {
    return await _channel.invokeMethod<String>('$_name/getWifiSSID') ?? '';
  }

  Future<String> getLocalIpAddress() async {
    return await _channel.invokeMethod<String>('$_name/getLocalIpAddress') ?? '';
  }

  Future<String> getWifiAddress() async {
    return await _channel.invokeMethod<String>('$_name/getWifiAddress') ?? '';
  }

  Future<List<String>> getWifiAddressList() async {
    final res = await _channel.invokeMethod<List>('$_name/getWifiAddressList') ?? [];
    return List<String>.from(res);
  }
}
