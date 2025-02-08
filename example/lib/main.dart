import 'package:flutter/material.dart';
import 'package:than_pkg/than_pkg.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();

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
      // String path = '/storage/emulated/0/test.pdf';
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
      // await ThanPkg.platform.getWifiAddressList();
      // await ThanPkg.platform
      //     .genPdfCover(outDirPath: outDirPath, pdfPathList: pdfPathList);

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
