abstract class ThanPkgInterface {
  Future<String?> getPlatformVersion();
  Future<String?> getLocalIpAddress();
  Future<String?> getWifiAddress();
  Future<List<dynamic>?> getWifiAddressList();
  Future<bool> openUrl({required String url});
  Future<void> toggleFullScreen({required bool isFullScreen});
  Future<void> genPdfCover({
    required String outDirPath,
    required List<String> pdfPathList,
    int iconSize = 300,
  });
  Future<void> toggleKeepScreen({required bool isKeep});
  Future<bool> isStoragePermissionGranted();
  Future<void> requestStoragePermission();
  Future<void> checkAndRequestPackageInstallPermission();
  Future<String?> getDeviceId();
}
