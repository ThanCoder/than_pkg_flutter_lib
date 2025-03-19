import 'dart:io';

import 'package:flutter_test/flutter_test.dart';
import 'package:than_pkg/than_pkg.dart';

void main() async {
  if (!Platform.isAndroid) return;
  group('android_wifi_util:', () {
    test('getLocalIpAddress', () async {
      final res = await ThanPkg.android.wifi.getLocalIpAddress();
      expect(res, isA<String>());
    });
    test('getWifiAddress', () async {
      final res = await ThanPkg.android.wifi.getWifiAddress();
      expect(res, isA<String>());
    });
    test('getWifiSSID', () async {
      final res = await ThanPkg.android.wifi.getWifiSSID();
      expect(res, isA<String>());
    });
    test('getWifiAddressList', () async {
      final res = await ThanPkg.android.wifi.getWifiAddressList();
      expect(res, isA<List<String>>());
    });
  });
}
