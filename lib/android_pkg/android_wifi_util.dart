import 'package:flutter/services.dart';

class AndroidWifiUtil {
  static final AndroidWifiUtil wifi = AndroidWifiUtil._();
  AndroidWifiUtil._();
  factory AndroidWifiUtil() => wifi;

  final _channel = const MethodChannel('than_pkg');

  Future<String> getWifiSSID() async {
    return await _channel.invokeMethod<String>('getWifiSSID') ?? '';
  }

  Future<String> getLocalIpAddress() async {
    return await _channel.invokeMethod('getLocalIpAddress') ?? '';
  }

  Future<String> getWifiAddress() async {
    return await _channel.invokeMethod('getWifiAddress') ?? '';
  }

  Future<List<String>> getWifiAddressList() async {
    final res = await _channel.invokeMethod<List>('getWifiAddressList') ?? [];
    return List<String>.from(res);
  }
}
