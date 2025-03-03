import 'dart:io';

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
  String imageUri = '';

  @override
  void initState() {
    super.initState();
    init();
  }

  void init() async {
    await ThanPkg.android.app.toggleKeepScreenOn(isKeep: true);
  }

  void _test() async {
    try {
      if (!await ThanPkg.android.permission.isCameraPermission()) {
        await ThanPkg.android.permission.requestCameraPermission();
        return;
      }
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
        body: Center(
          child: imageUri.isEmpty
              ? Text('Camera')
              : Image.file(
                  File(imageUri),
                ),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: _test,
          child: Icon(Icons.get_app),
        ),
      ),
    );
  }
}
