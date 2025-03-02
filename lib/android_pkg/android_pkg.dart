import 'package:than_pkg/android_pkg/index.dart';

class AndroidPkg {
  static final AndroidPkg android = AndroidPkg._();
  AndroidPkg._();
  factory AndroidPkg() => android;

  AndroidAppUtil get app => AndroidAppUtil.app;
  AndroidCamera get camera => AndroidCamera.camera;
  AndroidPermission get permission => AndroidPermission.permission;
  AndroidThumbnail get thumbnail => AndroidThumbnail.thumbnail;
  AndroidWifiUtil get wifi => AndroidWifiUtil.wifi;
}
