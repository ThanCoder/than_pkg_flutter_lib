import 'dart:io';

import 'package:flutter/services.dart';

class AndroidPermission {
  static final AndroidPermission permission = AndroidPermission._();
  AndroidPermission._();
  factory AndroidPermission() => permission;

  final _channel = const MethodChannel('than_pkg');
  final _name = 'permissionUtil';

  //for request
  Future<void> checkCanRequestPackageInstallsPermission() async {
    await _channel
        .invokeMethod('$_name/checkCanRequestPackageInstallsPermission');
  }

  Future<bool> isPackageInstallPermission() async {
    return await _channel
            .invokeMethod<bool>('$_name/isPackageInstallPermission') ??
        false;
  }

  Future<bool> isStoragePermissionGranted() async {
    if (!Platform.isAndroid) return true;
    return await _channel
            .invokeMethod<bool>('$_name/isStoragePermissionGranted') ??
        false;
  }

  Future<bool> isCameraPermission() async {
    return await _channel.invokeMethod<bool>('$_name/isCameraPermission') ??
        false;
  }

  Future<bool> isLocationPermission() async {
    return await _channel.invokeMethod<bool>('$_name/isLocationPermission') ??
        false;
  }

  Future<void> requestStoragePermission() async {
    if (!Platform.isAndroid) return;
    await _channel.invokeMethod('$_name/requestStoragePermission');
  }

  Future<void> requestPackageInstallPermission() async {
    await _channel.invokeMethod('$_name/requestPackageInstallPermission');
  }

  Future<void> requestCameraPermission() async {
    await _channel.invokeMethod('$_name/requestCameraPermission');
  }

  Future<void> requestLocationPermission() async {
    await _channel.invokeMethod('$_name/requestLocationPermission');
  }
}
