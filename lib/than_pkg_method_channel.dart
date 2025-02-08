import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'than_pkg_platform_interface.dart';

class ThanPkgMethodChannel extends ThanPkgPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('than_pkg');
}
