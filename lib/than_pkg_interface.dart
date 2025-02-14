import 'package:than_pkg/enums/screen_orientation_types.dart';

abstract class ThanPkgInterface {
  Future<List<String>> getWifiAddressList();
  Future<bool> openUrl({required String url});
  Future<void> toggleFullScreen({required bool isFullScreen});
  Future<void> genPdfCover({
    required String outDirPath,
    required List<String> pdfPathList,
    int iconSize = 300,
  });

  Future<void> genVideoCover({
    required String outDirPath,
    required List<String> videoPathList,
    int iconSize = 300,
  });
  //android only
  Future<ScreenOrientationTypes?> checkScreenOrientation();
  Future<void> requestScreenOrientation({
    required ScreenOrientationTypes type,
    bool reverse = false,
  });
  Future<Map<String, dynamic>> getAndroidDeviceInfo();
  Future<bool> isStoragePermissionGranted();
  Future<void> requestStoragePermission();
  Future<void> checkAndRequestPackageInstallPermission();
  Future<String?> getDeviceId();
  Future<String?> getPlatformVersion();
  Future<String?> getLocalIpAddress();
  Future<String?> getWifiAddress();
  Future<void> toggleKeepScreen({required bool isKeep});
  Future<String?> getAppRootPath();
  Future<String?> getAppExternalPath();

  Future<String?> getAppFilePath();
  Future<bool> isAppSystemThemeDarkMode();
  Future<bool> isAppInternetConnected();
  Future<int> getAppBatteryLevel();
  Future<Map> getLastKnownLocation();
  Future<List<Map>> getInstalledApps();
  Future<String?> getWifiSSID();
}
