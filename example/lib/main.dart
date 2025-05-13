import 'dart:io';

import 'package:flutter/material.dart';
import 'package:than_pkg/than_pkg.dart';
import 'package:than_pkg/types/src_dist_type.dart';

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
  String imageUri = '';
  final TextEditingController textEditingController = TextEditingController();

  @override
  void initState() {
    super.initState();
    init();
  }

  void init() async {
    if (Platform.isAndroid) {
      await ThanPkg.android.app.toggleKeepScreenOn(isKeep: true);
    }
  }

  void _test() async {
    try {
      // extension
      DateTime.now().toParseTime();
      DateTime.now().toTimeAgo();
      //double
      0.0.toFileSizeLabel();
      //FileSystemEntityExtension
      // FileSystemEntityExtension.getName(withExt: false)

      //PlatformExtension
      PlatformExtension.isDesktop();
      PlatformExtension.isMobile();

      //StringExtension
      "".toCaptalize();
      "".getName();
      "".getExt();
      //TextEditingControllerExtension
      TextEditingController().selectAll();

      if (Platform.isAndroid) {
        if (!await ThanPkg.android.permission.isStoragePermissionGranted()) {
          await ThanPkg.android.permission.requestStoragePermission();
          return;
        }
      }

      final path = await ThanPkg.platform.getAppExternalPath();

      await ThanPkg.platform.genPdfThumbnail(pathList: [
        SrcDistType(src: '$path/Download/1-50.pdf', dist: '$path/test.png'),
      ]);
      await ThanPkg.platform.genVideoThumbnail(pathList: [
        SrcDistType(
            src: '$path/Download/catch.mp4', dist: '$path/catch-video.png'),
      ]);

      // final path = '/home/thancoder/Videos/【GMV】- BOSS B_TCH.mp4';
      // await ThanPkg.platform.genVideoThumbnail(pathList: [
      //   SrcDistType(src: path, dist: '/home/thancoder/Videos/test.png')
      // ]);
      // await ThanPkg.platform.genPdfThumbnail(pathList: [
      //   SrcDistType(
      //       src: '/home/thancoder/Downloads/mmbook/သရက်စိုက်ပျိုးနည်း.pdf',
      //       dist: '/home/thancoder/Downloads/mmbook/test.png'),
      // ]);
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
          // onPressed: () {
          //   ThanPkg.platform.launch(
          //       '/home/thancoder/Downloads/mmbook/သရက်စိုက်ပျိုးနည်း.pdf');
          // },
          child: Icon(Icons.get_app),
        ),
      ),
    );
  }
}
