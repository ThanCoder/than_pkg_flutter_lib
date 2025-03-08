import 'dart:async';

import 'package:flutter/services.dart';

class AndroidCamera {
  static final AndroidCamera camera = AndroidCamera._();
  AndroidCamera._();
  factory AndroidCamera() => camera;

  final _channel = const MethodChannel('than_pkg');
  final _name = 'cameraUtil';

  void _cameraListener({required void Function(String imageUri) callback}) {
    _channel.setMethodCallHandler((call) async {
      if (call.method == 'onCameraResult') {
        final uri = call.arguments['data'] ?? '';
        callback(uri);
      }
    });
  }

  Future<String> openCamera() async {
    final completer = Completer<String>();
    _cameraListener(
      callback: (imageUri) {
        completer.complete(imageUri);
      },
    );
    try {
      await _channel.invokeMethod('$_name/openCamera');
    } catch (e) {
      completer.completeError(e);
    }
    return completer.future;
  }
}
