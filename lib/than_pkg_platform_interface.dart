import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'than_pkg_method_channel.dart';

abstract class ThanPkgPlatform extends PlatformInterface {
  /// Constructs a ThanPkgPlatform.
  ThanPkgPlatform() : super(token: _token);

  static final Object _token = Object();

  static ThanPkgPlatform _instance = MethodChannelThanPkg();

  /// The default instance of [ThanPkgPlatform] to use.
  ///
  /// Defaults to [MethodChannelThanPkg].
  static ThanPkgPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [ThanPkgPlatform] when
  /// they register themselves.
  static set instance(ThanPkgPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
