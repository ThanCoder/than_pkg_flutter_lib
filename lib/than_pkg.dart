import 'dart:io';

import 'package:than_pkg/than_pkg_android.dart';
import 'package:than_pkg/than_pkg_interface.dart';

class ThanPkg implements ThanPkgInterface {
  static ThanPkg get platform => _createInstance();

  static ThanPkg _createInstance() {
    if (Platform.isAndroid) {
      return ThanPkgAndroid();
    }
    return ThanPkg();
  }

  @override
  Future<void> genPdfCover(
      {required String outDirPath,
      required List<String> pdfPathList,
      int iconSize = 300}) {
    // TODO: implement genPdfCover
    throw UnimplementedError();
  }

  @override
  Future<String?> getLocalIpAddress() {
    // TODO: implement getLocalIpAddress
    throw UnimplementedError();
  }

  @override
  Future<String?> getPlatformVersion() {
    // TODO: implement getPlatformVersion
    throw UnimplementedError();
  }

  @override
  Future<String?> getWifiAddress() {
    // TODO: implement getWifiAddress
    throw UnimplementedError();
  }

  @override
  Future<List?> getWifiAddressList() {
    // TODO: implement getWifiAddressList
    throw UnimplementedError();
  }

  @override
  Future<bool> openUrl({required String url}) {
    // TODO: implement openUrl
    throw UnimplementedError();
  }

  @override
  Future<void> toggleFullScreen({required bool isFullScreen}) {
    // TODO: implement toggleFullScreen
    throw UnimplementedError();
  }

  @override
  Future<String?> getDeviceId() {
    // TODO: implement getDeviceId
    throw UnimplementedError();
  }

  @override
  Future<void> toggleKeepScreen({required bool isKeep}) {
    // TODO: implement toggleKeepScreen
    throw UnimplementedError();
  }

  @override
  Future<bool> isStoragePermissionGranted() {
    // TODO: implement isStoragePermissionGranted
    throw UnimplementedError();
  }

  @override
  Future<void> requestStoragePermission() {
    // TODO: implement requestStoragePermission
    throw UnimplementedError();
  }

  @override
  Future<void> checkAndRequestPackageInstallPermission() {
    // TODO: implement checkAndRequestPackageInstallPermission
    throw UnimplementedError();
  }
}
