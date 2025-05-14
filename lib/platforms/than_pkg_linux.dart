import 'dart:io';
import 'package:than_pkg/than_pkg.dart';
import 'package:than_pkg/types/src_dist_type.dart';

class ThanPkgLinux extends ThanPkg {
  @override
  Future<bool> isStoragePermissionGranted() async {
    return true;
  }

  @override
  Future<void> toggleKeepScreen({required bool isKeep}) async {}

  @override
  Future<void> toggleFullScreen({required bool isFullScreen}) async {
    await windowManager.setFullScreen(isFullScreen);
  }

  @override
  Future<bool> isAppSystemThemeDarkMode() async {
    return false;
  }

  @override
  Future<String?> getAppRootPath() async {
    return Directory.current.path;
  }

  @override
  Future<String?> getAppExternalPath() async {
    return Platform.environment['HOME'] ?? '';
  }

  @override
  Future<void> genVideoCover({
    required String outDirPath,
    required List<String> videoPathList,
    int iconSize = 300,
  }) async {
    await ThanPkg.linux.thumbnail.genVideoCoverList(
      outDirPath: outDirPath,
      videoPathList: videoPathList,
    );
  }

  @override
  Future<void> genPdfCover({
    required String outDirPath,
    required List<String> pdfPathList,
    int iconSize = 300,
  }) async {
    await ThanPkg.linux.thumbnail.genPdfCoverList(
      outDirPath: outDirPath,
      pdfPathList: pdfPathList,
    );
  }

  @override
  Future<List<String>> getWifiAddressList() async {
    return await ThanPkg.linux.wifi.getWifiAddressList();
  }

  @override
  Future<void> launch(String source) async {
    await ThanPkg.linux.app.launch(source);
  }

  @override
  Future<void> genVideoThumbnail({
    required List<SrcDistType> pathList,
    int iconSize = 300,
    bool isOverride = false,
  }) async {
    await ThanPkg.linux.thumbnail.genVideoThumbnail(
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
    await ThanPkg.linux.thumbnail.genPdfThumbnail(
      pathList: pathList,
      isOverride: isOverride,
      iconSize: iconSize,
    );
  }
}
