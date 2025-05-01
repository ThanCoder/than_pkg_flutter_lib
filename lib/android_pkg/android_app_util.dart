import 'dart:io';

import 'package:flutter/services.dart';
import 'package:mime/mime.dart';
import 'package:than_pkg/enums/screen_orientation_types.dart';

class AndroidAppUtil {
  static final AndroidAppUtil app = AndroidAppUtil._();
  AndroidAppUtil._();
  factory AndroidAppUtil() => app;

  final _channel = const MethodChannel('than_pkg');
  final _name = 'appUtil';

  ///
  /// auto choose file and url
  ///
  /// is supported url,pdf,video
  ///
  Future<void> launch(String source) async {
    if (source.isEmpty) throw Exception('`source` is empty');
    if (source.startsWith('http')) {
      // url
      await launchUrl(source);
    } else {
      //file
      await launchFile(source);
    }
  }

  Future<void> launchFile(String source) async {
    if (!File(source).existsSync()) throw Exception('`source` is exists');
    final mime = lookupMimeType(source) ?? '';
    if (mime.startsWith('video')) {
      await openVideoWithIntent(path: source);
      return;
    }
    if (mime.startsWith('application/pdf')) {
      await openPdfWithIntent(path: source);
      return;
    }
    throw Exception('mime:`$mime` not supported');
  }

  Future<void> launchUrl(String url) async {
    await _channel.invokeMethod('$_name/openUrl', {'url': url});
  }

  Future<int> getSdkInt() async {
    return await _channel.invokeMethod<int>('$_name/getSdkInt') ?? 0;
  }

  Future<Map<String, dynamic>> getDeviceInfo() async {
    final res = await _channel.invokeMethod<Map>('$_name/getDeviceInfo') ?? {};
    return Map<String, dynamic>.from(res);
  }

  Future<void> openPdfWithIntent({required String path}) async {
    await _channel.invokeMethod('$_name/openPdfWithIntent', {'path': path});
  }

  Future<void> openVideoWithIntent({required String path}) async {
    await _channel.invokeMethod('$_name/openVideoWithIntent', {'path': path});
  }

  Future<void> installApk({required String path}) async {
    await _channel.invokeMethod('$_name/installApk', {'path': path});
  }

  Future<void> openUrl({required String url}) async {
    await _channel.invokeMethod('$_name/openUrl', {'url': url});
  }

  Future<void> showToast(String message) async {
    await _channel.invokeMethod('$_name/showToast', {'message': message});
  }

  Future<void> hideFullScreen() async {
    await _channel.invokeMethod('$_name/hideFullScreen');
  }

  Future<void> showFullScreen() async {
    await _channel.invokeMethod('$_name/showFullScreen');
  }

  Future<bool> isFullScreen() async {
    return await _channel.invokeMethod<bool>('$_name/isFullScreen') ?? false;
  }

  Future<List<Map<String, dynamic>>> getInstalledAppsList() async {
    final res =
        await _channel.invokeMethod<List>('$_name/getInstalledAppsList') ?? [];
    return res.map((map) => Map<String, dynamic>.from(map)).toList();
  }

  Future<int> getBatteryLevel() async {
    return await _channel.invokeMethod<int>('$_name/getBatteryLevel') ?? 0;
  }

  Future<bool> isInternetConnected() async {
    return await _channel.invokeMethod<bool>('$_name/isInternetConnected') ??
        false;
  }

  Future<bool> isDarkModeEnabled() async {
    return await _channel.invokeMethod<bool>('$_name/isDarkModeEnabled') ??
        false;
  }

  Future<String> getFilesDir() async {
    return await _channel.invokeMethod<String>('$_name/getFilesDir') ?? '';
  }

  Future<String> getExternalFilesDir() async {
    return await _channel.invokeMethod<String>('$_name/getExternalFilesDir') ??
        '';
  }

  Future<String> getAppExternalPath() async {
    return await _channel.invokeMethod<String>('$_name/getAppExternalPath') ??
        '';
  }

  Future<void> requestOrientation({
    required ScreenOrientationTypes type,
    bool isReverse = false,
  }) async {
    await _channel.invokeMethod('$_name/requestOrientation', {
      'type': type.name,
      'reverse': isReverse,
    });
  }

  Future<ScreenOrientationTypes?> checkOrientation() async {
    final res = await _channel.invokeMethod<String>('$_name/checkOrientation');
    if (res == null) return null;
    return ScreenOrientationTypesExtension.getType(res);
  }

  Future<ScreenOrientationTypes?> getOrientation() async {
    final res = await _channel.invokeMethod<String>('$_name/checkOrientation');
    if (res == null) return null;
    return ScreenOrientationTypesExtension.getType(res);
  }

  Future<String> getPlatformVersion() async {
    return await _channel.invokeMethod<String>('$_name/getPlatformVersion') ??
        '';
  }

  Future<void> toggleKeepScreenOn({required bool isKeep}) async {
    await _channel
        .invokeMethod('$_name/toggleKeepScreenOn', {'is_keep': isKeep});
  }

  Future<String> getDeviceId() async {
    return await _channel.invokeMethod<String>('$_name/getDeviceId') ?? '';
  }
}
