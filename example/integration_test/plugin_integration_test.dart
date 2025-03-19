import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';
import 'package:than_pkg/enums/screen_orientation_types.dart';

import 'package:than_pkg/than_pkg.dart';

void main() {
  IntegrationTestWidgetsFlutterBinding.ensureInitialized();

  testWidgets('checkOrientation', (widgetTester) async {
    final res = await ThanPkg.android.app.checkOrientation();
    expect(res, isA<ScreenOrientationTypes>());
    expect(res, isNotNull);
  });

  testWidgets('getAppExternalPath', (widgetTester) async {
    final res = await ThanPkg.android.app.getAppExternalPath();
    expect(res, isA<String>());
    expect(res, isNotEmpty);
  });
  testWidgets('getBatteryLevel', (widgetTester) async {
    final res = await ThanPkg.android.app.getBatteryLevel();
    expect(res, isA<int>());
    expect(res, isNotNaN);
  });
  testWidgets('getDeviceId', (widgetTester) async {
    final res = await ThanPkg.android.app.getDeviceId();
    expect(res, isA<String>());
    expect(res, isNotEmpty);
  });
  testWidgets('getDeviceInfo', (widgetTester) async {
    final res = await ThanPkg.android.app.getDeviceInfo();
    expect(res, isA<Map<String, dynamic>>());
  });
  testWidgets('getExternalFilesDir', (widgetTester) async {
    final res = await ThanPkg.android.app.getExternalFilesDir();
    expect(res, isA<String>());
    expect(res, isNotEmpty);
  });
  testWidgets('getFilesDir', (widgetTester) async {
    final res = await ThanPkg.android.app.getFilesDir();
    expect(res, isA<String>());
    expect(res, isNotEmpty);
  });
  testWidgets('getInstalledAppsList', (widgetTester) async {
    final res = await ThanPkg.android.app.getInstalledAppsList();
    expect(res, isA<List<Map<String, dynamic>>>());
  });
  testWidgets('getPlatformVersion', (widgetTester) async {
    final res = await ThanPkg.android.app.getPlatformVersion();
    expect(res, isA<String>());
    expect(res, isNotEmpty);
  });
  testWidgets('getSdkInt', (widgetTester) async {
    final res = await ThanPkg.android.app.getSdkInt();
    expect(res, isA<int>());
    expect(res, isNotNaN);
  });
  testWidgets('isInternetConnected', (widgetTester) async {
    final res = await ThanPkg.android.app.isInternetConnected();
    expect(res, isA<bool>());
  });

  //wifi
  testWidgets('getLocalIpAddress', (widgetTester) async {
    final res = await ThanPkg.android.wifi.getLocalIpAddress();
    expect(res, isA<String>());
  });
  testWidgets('getWifiAddress', (widgetTester) async {
    final res = await ThanPkg.android.wifi.getWifiAddress();
    expect(res, isA<String>());
  });
  testWidgets('getWifiSSID', (widgetTester) async {
    final res = await ThanPkg.android.wifi.getWifiSSID();
    expect(res, isA<String>());
  });
  testWidgets('getWifiAddressList', (widgetTester) async {
    final res = await ThanPkg.android.wifi.getWifiAddressList();
    expect(res, isA<List<String>>());
  });
}
