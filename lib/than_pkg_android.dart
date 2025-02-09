import 'package:flutter/services.dart';
import 'package:than_pkg/enums/screen_orientation_types.dart';
import 'package:than_pkg/than_pkg.dart';

class ThanPkgAndroid extends ThanPkg {
  final channel = const MethodChannel('than_pkg');

  @override
  Future<ScreenOrientationTypes?> checkScreenOrientation() async {
    final res = await channel.invokeMethod<String>('check_orientation');
    if (res == null) return null;
    if (res == ScreenOrientationTypes.Portrait.name) {
      return ScreenOrientationTypes.Portrait;
    } else if (res == ScreenOrientationTypes.Landscape.name) {
      return ScreenOrientationTypes.Landscape;
    }
    return null;
  }

  @override
  Future<void> requestScreenOrientation({
    required ScreenOrientationTypes type,
    bool reverse = false,
  }) async {
    await channel.invokeMethod('req_orientation', {
      'type': type.name,
      'reverse': reverse,
    });
  }

  @override
  Future<void> genVideoCover({
    required Comparable<String> outDirPath,
    required List<String> videoPathList,
    int iconSize = 300,
  }) async {
    await channel.invokeMethod('gen_video_thumbnail_list', {
      'path_list': videoPathList,
      'out_dir_path': outDirPath,
      'icon_size': iconSize,
    });
  }

  @override
  Future<Map<String, dynamic>> getAndroidDeviceInfo() async {
    final res = await channel.invokeMethod<Map>('get_android_device_info');
    if (res == null) return {};
    return Map<String, dynamic>.from(res);
  }

  @override
  Future<bool> isStoragePermissionGranted() async {
    final res =
        await channel.invokeMethod<bool>('check_storage_permission_granted');
    return res ?? false;
  }

  @override
  Future<void> checkAndRequestPackageInstallPermission() async {
    await channel.invokeMethod('check_req_package_install_permission');
  }

  @override
  Future<void> requestStoragePermission() async {
    await channel.invokeMethod('req_storage_permission');
  }

  @override
  Future<void> toggleKeepScreen({required bool isKeep}) async {
    await channel.invokeMethod('toggle_keep_screen', {'is_keep': isKeep});
  }

  @override
  Future<String?> getDeviceId() async {
    return await channel.invokeMethod<String>('get_device_id');
  }

  @override
  Future<void> genPdfCover(
      {required String outDirPath,
      required List<String> pdfPathList,
      int iconSize = 300}) async {
    await channel.invokeMethod('genPdfCover', {
      'out_dir_path': outDirPath,
      'pdf_path_list': pdfPathList,
      'size': iconSize,
    });
  }

  @override
  Future<String?> getLocalIpAddress() async {
    return await channel.invokeMethod<String>('get_local_ip_address');
  }

  @override
  Future<String?> getPlatformVersion() async {
    final version = await channel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<String?> getWifiAddress() async {
    return await channel.invokeMethod<String>('get_wifi_address');
  }

  @override
  Future<List<String>> getWifiAddressList() async {
    final res = await channel.invokeMethod<List>('get_wifi_address_list');
    if (res == null) return [];
    return List<String>.from(res.map((obj) => obj.toString()));
  }

  @override
  Future<bool> openUrl({required String url}) async {
    final res = await channel.invokeMethod<bool>('open_url', {'url': url});
    return res ?? false;
  }

  @override
  Future<void> toggleFullScreen({required bool isFullScreen}) async {
    if (isFullScreen) {
      SystemChrome.setEnabledSystemUIMode(
          SystemUiMode.immersiveSticky); // Fullscreen mode
      await channel.invokeMethod('show_fullscreen');
    } else {
      SystemChrome.setEnabledSystemUIMode(SystemUiMode.edgeToEdge);
      await channel.invokeMethod('hide_fullscreen');
    }
  }
}
