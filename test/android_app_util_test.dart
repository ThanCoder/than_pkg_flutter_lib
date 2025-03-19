import 'dart:io';

import 'package:flutter_test/flutter_test.dart';
import 'package:than_pkg/enums/screen_orientation_types.dart';
import 'package:than_pkg/than_pkg.dart';

void main() async {
  if (!Platform.isAndroid) return;
  group('android_app_util', () {
    test('checkOrientation', () async {
      final res = await ThanPkg.android.app.checkOrientation();
      expect(res, isA<ScreenOrientationTypes>());
      expect(res, isNotNull);
    });
    test('getAppExternalPath', () async {
      final res = await ThanPkg.android.app.getAppExternalPath();
      expect(res, isA<String>());
      expect(res, isNotEmpty);
    });
    test('getBatteryLevel', () async {
      final res = await ThanPkg.android.app.getBatteryLevel();
      expect(res, isA<int>());
      expect(res, isNotNaN);
    });
    test('getDeviceId', () async {
      final res = await ThanPkg.android.app.getDeviceId();
      expect(res, isA<String>());
      expect(res, isNotEmpty);
    });
    test('getDeviceInfo', () async {
      final res = await ThanPkg.android.app.getDeviceInfo();
      expect(res, isA<Map<String, dynamic>>());
    });
    test('getExternalFilesDir', () async {
      final res = await ThanPkg.android.app.getExternalFilesDir();
      expect(res, isA<String>());
      expect(res, isNotEmpty);
    });
    test('getFilesDir', () async {
      final res = await ThanPkg.android.app.getFilesDir();
      expect(res, isA<String>());
      expect(res, isNotEmpty);
    });
    test('getInstalledAppsList', () async {
      final res = await ThanPkg.android.app.getInstalledAppsList();
      expect(res, isA<List<Map<String, dynamic>>>());
    });
    test('getPlatformVersion', () async {
      final res = await ThanPkg.android.app.getPlatformVersion();
      expect(res, isA<String>());
      expect(res, isNotEmpty);
    });
    test('getSdkInt', () async {
      final res = await ThanPkg.android.app.getSdkInt();
      expect(res, isA<int>());
      expect(res, isNotNaN);
    });
    test('isInternetConnected', () async {
      final res = await ThanPkg.android.app.isInternetConnected();
      expect(res, isA<bool>());
    });
  });
}
