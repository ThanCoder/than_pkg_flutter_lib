import 'package:plugin_platform_interface/plugin_platform_interface.dart';

abstract class ThanPkgPlatform extends PlatformInterface {
  /// Constructs a ThanPkgPlatform.
  ThanPkgPlatform() : super(token: _token);

  static final Object _token = Object();
}
