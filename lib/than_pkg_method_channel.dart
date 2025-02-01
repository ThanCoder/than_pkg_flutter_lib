import 'dart:io';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'than_pkg_platform_interface.dart';

/// An implementation of [ThanPkgPlatform] that uses method channels.
class ThanPkgMethodChannel extends ThanPkgPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('than_pkg');

  //singleton
  static final ThanPkgMethodChannel _instance =
      ThanPkgMethodChannel._internal();
  //private constructor
  ThanPkgMethodChannel._internal();

  ///get
  // factory ThanPkgMethodChannel.instance() => _instance;
  static ThanPkgMethodChannel get instance => _instance;

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  Future<String?> getTesting() async {
    return await methodChannel.invokeMethod<String>('test');
  }

  Future<String?> getLocalIpAddress() async {
    return await methodChannel.invokeMethod<String>('get_local_ip_address');
  }

  Future<String?> getWifiAddress() async {
    return await methodChannel.invokeMethod<String>('get_wifi_address');
  }

  Future<List<dynamic>?> getWifiAddressList() async {
    return await methodChannel
        .invokeMethod<List<dynamic>>('get_wifi_address_list');
  }

  Future<bool> openUrl({required String url}) async {
    final res =
        await methodChannel.invokeMethod<bool>('open_url', {'url': url});
    return res ?? false;
  }

  Future<void> toggleFullScreen({required bool isFullScreen}) async {
    if (Platform.isAndroid) {
      if (isFullScreen) {
        SystemChrome.setEnabledSystemUIMode(
            SystemUiMode.immersiveSticky); // Fullscreen mode
        await methodChannel.invokeMethod('show_fullscreen');
      } else {
        SystemChrome.setEnabledSystemUIMode(SystemUiMode.edgeToEdge);
        await methodChannel.invokeMethod('hide_fullscreen');
      }
    }
  }

  Future<void> genPdfCover({
    required String outDirPath,
    required List<String> pdfPathList,
    int iconSize = 300,
  }) async {
    await methodChannel.invokeMethod('genPdfCover', {
      'out_dir_path': outDirPath,
      'pdf_path_list': pdfPathList,
      'size': iconSize,
    });
  }

  // Future<bool> androidStoragePermissionGranted() async {
  //   final res = await methodChannel
  //       .invokeMethod<bool>('androidStoragePermissionGranted');
  //   return res ?? false;
  // }

  // Future<void> androidRequestStoragePermission() async {
  //   await methodChannel.invokeMethod('androidRequestStoragePermission');
  // }
}
