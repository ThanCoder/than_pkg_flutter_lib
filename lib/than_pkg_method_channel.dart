import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'than_pkg_platform_interface.dart';

/// An implementation of [ThanPkgPlatform] that uses method channels.
class MethodChannelThanPkg extends ThanPkgPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('than_pkg');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
