import 'package:flutter/material.dart';
import 'package:than_pkg/enums/screen_orientation_types.dart';
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
    await ThanPkg.android.app.toggleKeepScreenOn(isKeep: true);
  }

  void _test() async {
    try {
      final type = await ThanPkg.android.app.getOrientation();
      if (type == null) return;
      if (type == ScreenOrientationTypes.Portrait) {
        await ThanPkg.android.app
            .requestOrientation(type: ScreenOrientationTypes.Landscape);
        await ThanPkg.android.app.showFullScreen();
      } else {
        ThanPkg.android.app
            .requestOrientation(type: ScreenOrientationTypes.Portrait);
        await ThanPkg.android.app.hideFullScreen();
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
            child: TextField(
          controller: textEditingController,
          decoration: InputDecoration(hintText: 'path'),
        )),
        floatingActionButton: FloatingActionButton(
          onPressed: _test,
          child: Icon(Icons.get_app),
        ),
      ),
    );
  }
}
