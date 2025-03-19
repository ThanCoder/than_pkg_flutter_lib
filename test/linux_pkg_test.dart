import 'dart:io';

import 'package:flutter_test/flutter_test.dart';
import 'package:than_pkg/than_pkg.dart';

void main() async {
  if (!Platform.isLinux) return;
  group('wifi_util:', () {
    test('getWifiAddressList', () async {
      final res = await ThanPkg.linux.wifi.getWifiAddressList();
      expect(res, isA<List<String>>());
      expect(res.isNotEmpty, true);
    });
  });
  group('than_pkg_linux:', () {
    test('getAppRootPath', () async {
      String? res = await ThanPkg.platform.getAppRootPath();
      expect(res, isA<String>());
      expect(res, isNotNull);
    });
    test('getAppExternalPath', () async {
      final res = await ThanPkg.platform.getAppExternalPath();
      expect(res, isA<String>());
      expect(res, isNotNull);
    });
  });
}
