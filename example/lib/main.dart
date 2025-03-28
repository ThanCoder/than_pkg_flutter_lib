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
      // ThanPkg.linux.app.launch('/home/thancoder/Videos/【GMV】- BOSS B_TCH.mp4');
      ThanPkg.platform.launch('/home/thancoder/Videos/【GMV】- BOSS B_TCH.mp4');
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
