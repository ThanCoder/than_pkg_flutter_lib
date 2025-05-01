import 'package:than_pkg/enums/screen_orientation_types.dart';
import 'package:than_pkg/than_pkg.dart';
import 'package:than_pkg/types/src_dist_type.dart';

class ThanPkgAndroid extends ThanPkg {
  @override
  Future<String> getWifiSSID() async {
    return await ThanPkg.android.wifi.getWifiSSID();
  }

  @override
  Future<List<Map>> getInstalledApps() async {
    return await ThanPkg.android.app.getInstalledAppsList();
  }

  @override
  Future<int> getAppBatteryLevel() async {
    return await ThanPkg.android.app.getBatteryLevel();
  }

  @override
  Future<bool> isAppInternetConnected() async {
    return await ThanPkg.android.app.isInternetConnected();
  }

  @override
  Future<bool> isAppSystemThemeDarkMode() async {
    return await ThanPkg.android.app.isDarkModeEnabled();
  }

  @override
  Future<String> getAppFilePath() async {
    return await ThanPkg.android.app.getFilesDir();
  }

  @override
  Future<String> getAppRootPath() async {
    return await ThanPkg.android.app.getExternalFilesDir();
  }

  @override
  Future<String> getAppExternalPath() async {
    return await ThanPkg.android.app.getAppExternalPath();
  }

  @override
  Future<ScreenOrientationTypes?> checkScreenOrientation() async {
    return await ThanPkg.android.app.checkOrientation();
  }

  @override
  Future<void> requestScreenOrientation({
    required ScreenOrientationTypes type,
    bool reverse = false,
  }) async {
    await ThanPkg.android.app
        .requestOrientation(type: type, isReverse: reverse);
  }

  @override
  Future<void> genVideoCover({
    required String outDirPath,
    required List<String> videoPathList,
    int iconSize = 300,
  }) async {
    await ThanPkg.android.thumbnail.genVideoThumbnailList(
      outDirPath: outDirPath,
      videoPathList: videoPathList,
      iconSize: iconSize,
    );
  }

  @override
  Future<Map<String, dynamic>> getAndroidDeviceInfo() async {
    return await ThanPkg.android.app.getDeviceInfo();
  }

  @override
  Future<bool> isStoragePermissionGranted() async {
    return await ThanPkg.android.permission.isStoragePermissionGranted();
  }

  @override
  Future<void> requestStoragePermission() async {
    await ThanPkg.android.permission.requestStoragePermission();
  }

  @override
  Future<void> toggleKeepScreen({required bool isKeep}) async {
    await ThanPkg.android.app.toggleKeepScreenOn(isKeep: isKeep);
  }

  @override
  Future<String> getDeviceId() async {
    return await ThanPkg.android.app.getDeviceId();
  }

  @override
  Future<void> genPdfCover(
      {required String outDirPath,
      required List<String> pdfPathList,
      int iconSize = 300}) async {
    await ThanPkg.android.thumbnail.genPdfCoverList(
      outDirPath: outDirPath,
      pdfPathList: pdfPathList,
    );
  }

  @override
  Future<String?> getLocalIpAddress() async {
    return await ThanPkg.android.wifi.getLocalIpAddress();
  }

  @override
  Future<String?> getPlatformVersion() async {
    return await ThanPkg.android.app.getPlatformVersion();
  }

  @override
  Future<String?> getWifiAddress() async {
    return await ThanPkg.android.wifi.getWifiAddress();
  }

  @override
  Future<List<String>> getWifiAddressList() async {
    return await ThanPkg.android.wifi.getWifiAddressList();
  }

  @override
  Future<void> openUrl({required String url}) async {
    await ThanPkg.android.app.openUrl(url: url);
  }

  @override
  Future<void> toggleFullScreen({required bool isFullScreen}) async {
    if (isFullScreen) {
      await ThanPkg.android.app.showFullScreen();
    } else {
      await ThanPkg.android.app.hideFullScreen();
    }
  }

  @override
  Future<void> launch(String source) async {
    await ThanPkg.android.app.launch(source);
  }

  @override
  Future<void> genVideoThumbnail({
    required List<SrcDistType> pathList,
    int iconSize = 300,
    bool isOverride = false,
  }) async {
    await ThanPkg.android.thumbnail.genVideoThumbnail2(
      pathList: pathList,
      isOverride: isOverride,
      iconSize: iconSize,
    );
  }

  @override
  Future<void> genPdfThumbnail({
    required List<SrcDistType> pathList,
    int iconSize = 300,
    bool isOverride = false,
  }) async {
    await ThanPkg.android.thumbnail.genPdfThumbnail(
      pathList: pathList,
      isOverride: isOverride,
      iconSize: iconSize,
    );
  }
}
