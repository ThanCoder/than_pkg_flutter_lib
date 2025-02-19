import 'package:flutter/material.dart';
import 'package:than_pkg/than_pkg.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await ThanPkg.windowManagerensureInitialized();

  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool isFullScreen = false;

  void _test() async {
    try {
      // setState(() {
      //   isFullScreen = !isFullScreen;
      // });
      // await ThanPkg.platform.toggleFullScreen(isFullScreen: isFullScreen);
      await ThanPkg.platform.genPdfCover(
          outDirPath: '/home/thancoder/Documents',
          pdfPathList: ['/home/thancoder/Documents/test.pdf']);
      print('success');
      //linux && android platform
      // final res = await ThanPkg.platform.isAppSystemThemeDarkMode();
      // final res = await ThanPkg.platform.getAppFilePath();
      // final res = await ThanPkg.platform.isAppInternetConnected();
      // final res = await ThanPkg.platform.getAppBatteryLevel();
      // final res = await ThanPkg.platform.getLastKnownLocation();
      // final res = await ThanPkg.platform.getInstalledApps();

      // print(res);

      // await ThanPkg.platform.getAppRootPath();
      // await ThanPkg.platform.getAppExternalPath();
      // await ThanPkg.platform.genPdfCover(
      //   outDirPath: '/home/thancoder/Downloads/novel_v3_out',
      //   pdfPathList: [
      //     '/home/thancoder/Downloads/novel_v3_out/test.pdf',
      //     '/home/thancoder/Downloads/novel_v3_out/who is he  book 1 - 25.pdf',
      //   ],
      // );
      // await ThanPkg.platform.genVideoCover(
      //   outDirPath: '/home/thancoder/Downloads/novel_v3_out',
      //   videoPathList: [
      //     '/home/thancoder/Videos/2002.mp4',
      //     '/home/thancoder/Videos/Live After.mp4',
      //   ],
      // );
      // final res = await ThanPkg.platform.getAndroidDeviceInfo();
      // final res = await ThanPkg.platform.getWifiAddressList();
      // final res = await ThanPkg.platform.checkScreenOrientation();
      // await ThanPkg.platform
      //     .requestScreenOrientation(type: ScreenOrientationTypes.Portrait);
      // print(res);

      //android platform
      // await ThanPkg.platform
      //     .openUrl(url: 'https://www.youtube.com/watch?v=MIWte3C6vYw');

      // await ThanPkg.platform.getPlatformVersion();
      // await ThanPkg.platform.getDeviceId();
      // await ThanPkg.platform.toggleKeepScreen(isKeep: false);
      // await ThanPkg.platform.toggleFullScreen(isFullScreen: !isFullScreen);
      // await ThanPkg.platform.isStoragePermissionGranted();
      // await ThanPkg.platform.requestStoragePermission();
      // await ThanPkg.platform.checkAndRequestPackageInstallPermission();
      // await ThanPkg.platform.getLocalIpAddress();
      // await ThanPkg.platform.getWifiAddress();
      // await ThanPkg.platform.getAndroidDeviceInfo();

      // setState(() {
      //   isFullScreen = !isFullScreen;
      // });
    } catch (e) {
      debugPrint(e.toString());
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      // theme: ThemeData.dark(),
      home: Scaffold(
        appBar: isFullScreen
            ? null
            : AppBar(
                title: const Text('test lib'),
              ),
        body: Placeholder(),
        floatingActionButton: FloatingActionButton(
          onPressed: _test,
          child: Icon(Icons.get_app),
        ),
      ),
    );
  }
}
