import 'dart:io';

import 'package:flutter/services.dart';

class AndroidPermission {
  static final AndroidPermission permission = AndroidPermission._();
  AndroidPermission._();
  factory AndroidPermission() => permission;

  final _channel = const MethodChannel('than_pkg');

  //for request
  Future<void> checkCanRequestPackageInstallsPermission() async {
    await _channel.invokeMethod('checkCanRequestPackageInstallsPermission');
  }

  Future<bool> isPackageInstallPermission() async {
    return await _channel.invokeMethod<bool>('isPackageInstallPermission') ??
        false;
  }

  Future<bool> isStoragePermissionGranted() async {
    if (!Platform.isAndroid) return true;
    return await _channel.invokeMethod<bool>('isStoragePermissionGranted') ??
        false;
  }

  Future<bool> isCameraPermission() async {
    return await _channel.invokeMethod<bool>('isCameraPermission') ?? false;
  }

  Future<bool> isLocationPermission() async {
    return await _channel.invokeMethod<bool>('isLocationPermission') ?? false;
  }

  Future<void> requestStoragePermission() async {
    if (!Platform.isAndroid) return;
    await _channel.invokeMethod('requestStoragePermission');
  }

  Future<void> requestPackageInstallPermission() async {
    await _channel.invokeMethod('requestPackageInstallPermission');
  }

  Future<void> requestCameraPermission() async {
    await _channel.invokeMethod('requestCameraPermission');
  }

  Future<void> requestLocationPermission() async {
    await _channel.invokeMethod('requestLocationPermission');
  }
}
