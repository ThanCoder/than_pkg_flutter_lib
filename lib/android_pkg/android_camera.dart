import 'package:flutter/services.dart';

class AndroidCamera {
  static final AndroidCamera camera = AndroidCamera._();
  AndroidCamera._();
  factory AndroidCamera() => camera;

  final _channel = const MethodChannel('than_pkg');

  // void _cameraListener({required VoidCallback callback}) {
  //   _channel.setMethodCallHandler((call) async {
  //     if (call.method == 'onCameraResult') {
  //       final uri = call.arguments['uri'];
  //       callback();
  //     }
  //   });
  // }

  // Future<void> openCamera({required VoidCallback callback}) async {
  //   _cameraListener(callback: callback);
  //   await _channel.invokeMethod('openCamera');
  // }
  Future<void> openCamera() async {
    await _channel.invokeMethod('openCamera');
  }
}
