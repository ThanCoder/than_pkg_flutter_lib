import 'package:than_pkg/linux_pkg/linux_app.dart';
import 'package:than_pkg/linux_pkg/linux_thumbnail.dart';
import 'package:than_pkg/linux_pkg/linux_wifi_util.dart';

class LinuxPkg {
  static final LinuxPkg linux = LinuxPkg._();
  LinuxPkg._();
  factory LinuxPkg() => linux;

  LinuxThumbnail get thumbnail => LinuxThumbnail.thumbnail;
  LinuxWifiUtil get wifi => LinuxWifiUtil.wifi;
  LinuxApp get app => LinuxApp.app;
}
