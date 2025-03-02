import 'package:flutter/services.dart';
import 'package:than_pkg/enums/screen_orientation_types.dart';
import 'package:than_pkg/than_pkg.dart';

class ThanPkgAndroid extends ThanPkg {
  

  @override
  Future<String?> getWifiSSID() async {
    final res = await channel.invokeMethod<String>('getWifiSSID');
    return res;
  }

  @override
  Future<List<Map>> getInstalledApps() async {
    final res = await channel.invokeMethod<List>('getInstalledAppsList');
    if (res == null) return [];
    return res.map((map) => Map<String, String>.from(map)).toList();
  }


  @override
  Future<int> getAppBatteryLevel() async {
    final res = await channel.invokeMethod<int>('getBatteryLevel');
    return res ?? -1;
  }

  @override
  Future<bool> isAppInternetConnected() async {
    final res = await channel.invokeMethod<bool>('isInternetConnected');
    return res ?? false;
  }

  @override
  Future<bool> isAppSystemThemeDarkMode() async {
    final res = await channel.invokeMethod<bool>('isDarkModeEnabled');
    return res ?? false;
  }

  @override
  Future<String?> getAppFilePath() async {
    final res = await channel.invokeMethod<String>('getFilesDir');
    return res;
  }

  @override
  Future<String?> getAppRootPath() async {
    final res = await channel.invokeMethod<String>('getExternalFilesDir');
    return res;
  }

  @override
  Future<String?> getAppExternalPath() async {
    final res = await channel.invokeMethod<String>('getAppExternalPath');
    return res;
  }

  @override
  Future<ScreenOrientationTypes?> checkScreenOrientation() async {
    final res = await channel.invokeMethod<String>('checkOrientation');
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
    await channel.invokeMethod('requestOrientation', {
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
    await channel.invokeMethod('genVideoThumbnailList', {
      'path_list': videoPathList,
      'out_dir_path': outDirPath,
      'icon_size': iconSize,
    });
  }

  @override
  Future<Map<String, dynamic>> getAndroidDeviceInfo() async {
    final res = await channel.invokeMethod<Map>('getDeviceInfo');
    if (res == null) return {};
    return Map<String, dynamic>.from(res);
  }

  @override
  Future<bool> isStoragePermissionGranted() async {
    final res =
        await channel.invokeMethod<bool>('isStoragePermissionGranted');
    return res ?? false;
  }

  @override
  Future<void> requestStoragePermission() async {
    await channel.invokeMethod('requestStoragePermission');
  }

  @override
  Future<void> toggleKeepScreen({required bool isKeep}) async {
    await channel.invokeMethod('toggleKeepScreenOn', {'is_keep': isKeep});
  }

  @override
  Future<String?> getDeviceId() async {
    return await channel.invokeMethod<String>('getDeviceId');
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
    return await channel.invokeMethod<String>('getLocalIpAddress');
  }

  @override
  Future<String?> getPlatformVersion() async {
    final version = await channel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<String?> getWifiAddress() async {
    return await channel.invokeMethod<String>('getWifiAddress');
  }

  @override
  Future<List<String>> getWifiAddressList() async {
    final res = await channel.invokeMethod<List>('getWifiAddressList');
    if (res == null) return [];
    return List<String>.from(res.map((obj) => obj.toString()));
  }

  @override
  Future<bool> openUrl({required String url}) async {
    final res = await channel.invokeMethod<bool>('openUrl', {'url': url});
    return res ?? false;
  }

  @override
  Future<void> toggleFullScreen({required bool isFullScreen}) async {
    if (isFullScreen) {
      SystemChrome.setEnabledSystemUIMode(
          SystemUiMode.immersiveSticky); // Fullscreen mode
      await channel.invokeMethod('showFullScreen');
    } else {
      SystemChrome.setEnabledSystemUIMode(SystemUiMode.edgeToEdge);
      await channel.invokeMethod('hideFullScreen');
    }
  }
}
