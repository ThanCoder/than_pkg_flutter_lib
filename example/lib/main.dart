import 'package:flutter/material.dart';
import 'package:than_pkg/than_pkg_method_channel.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  void _test() async {
    try {
      // String path = '/storage/emulated/0/test.pdf';

      // final isGranted = await Permission.manageExternalStorage.isGranted;
      // if (!isGranted) {
      //   await Permission.manageExternalStorage.request();
      // }

      // final res = await ThanPkgMethodChannel.instance.getLocalIpAddress();
      // final res2 = await ThanPkgMethodChannel.instance.getWifiAddress();
      // final addrs = await ThanPkgMethodChannel.instance.getWifiAddressList();
      // final res = await ThanPkgMethodChannel.instance
      //     .openUrl(url: 'https://www.youtube.com/watch?v=MIWte3C6vYw');
      debugPrint('click');
      setState(() {
        isFullScreen = !isFullScreen;
      });

      await ThanPkgMethodChannel.instance
          .toggleFullScreen(isFullScreen: isFullScreen);
    } catch (e) {
      debugPrint(e.toString());
    }
  }

  bool isFullScreen = false;

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
