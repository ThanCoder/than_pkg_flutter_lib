import 'package:flutter/services.dart';
import 'package:than_pkg/than_pkg.dart';

class ThanPkgAndroid extends ThanPkg {
  final channel = const MethodChannel('than_pkg');

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
  Future<List?> getWifiAddressList() async {
    return await channel.invokeMethod<List<dynamic>>('get_wifi_address_list');
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
