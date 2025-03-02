import 'package:flutter/services.dart';
import 'package:than_pkg/enums/screen_orientation_types.dart';

class AndroidAppUtil {
  static final AndroidAppUtil app = AndroidAppUtil._();
  AndroidAppUtil._();
  factory AndroidAppUtil() => app;

  final _channel = const MethodChannel('than_pkg');

  Future<void> openUrl({required String url}) async {
    await _channel.invokeMethod('openUrl', {'url': url});
  }

  Future<void> hideFullScreen() async {
    await _channel.invokeMethod('hideFullScreen');
  }

  Future<void> showFullScreen() async {
    await _channel.invokeMethod('showFullScreen');
  }

  Future<List<Map<String, dynamic>>> getInstalledAppsList() async {
    final res = await _channel.invokeMethod<List>('getInstalledAppsList') ?? [];
    return res.map((map) => Map<String, dynamic>.from(map)).toList();
  }

  Future<int> getBatteryLevel() async {
    return await _channel.invokeMethod<int>('getBatteryLevel') ?? 0;
  }

  Future<bool> isInternetConnected() async {
    return await _channel.invokeMethod<bool>('isInternetConnected') ?? false;
  }

  Future<bool> isDarkModeEnabled() async {
    return await _channel.invokeMethod<bool>('isDarkModeEnabled') ?? false;
  }

  Future<String> getFilesDir() async {
    return await _channel.invokeMethod<String>('getFilesDir') ?? '';
  }

  Future<String> getExternalFilesDir() async {
    return await _channel.invokeMethod<String>('getExternalFilesDir') ?? '';
  }

  Future<String> getAppExternalPath() async {
    return await _channel.invokeMethod<String>('getAppExternalPath') ?? '';
  }

  Future<void> requestOrientation({
    required ScreenOrientationTypes type,
    bool isReverse = false,
  }) async {
    await _channel.invokeMethod('requestOrientation', {
      'type': type,
      'reverse': isReverse,
    });
  }

  Future<bool> checkOrientation() async {
    return await _channel.invokeMethod<bool>('checkOrientation') ?? false;
  }

  Future<String> getPlatformVersion() async {
    return await _channel.invokeMethod<String>('getPlatformVersion') ?? '';
  }

  Future<void> toggleKeepScreenOn({required bool isKeep}) async {
    await _channel.invokeMethod('toggleKeepScreenOn', {'is_keep': isKeep});
  }

  Future<String> getDeviceId() async {
    return await _channel.invokeMethod<String>('getDeviceId') ?? '';
  }
}
