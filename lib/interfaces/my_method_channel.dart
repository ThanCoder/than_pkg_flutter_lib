import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

class MyMethodChannel {
  @visibleForTesting
  final channel = const MethodChannel('than_pkg');
}
