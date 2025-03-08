import 'dart:io';
import 'package:than_pkg/than_pkg.dart';
import 'package:window_manager/window_manager.dart';

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
    required Comparable<String> outDirPath,
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
}
