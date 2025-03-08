import 'dart:io';

import 'package:flutter/foundation.dart';

class LinuxWifiUtil {
  static final LinuxWifiUtil wifi = LinuxWifiUtil._();
  LinuxWifiUtil._();
  factory LinuxWifiUtil() => wifi;

  Future<List<String>> getWifiAddressList() async {
    List<String> list = [];
    try {
      ProcessResult result = await Process.run('ifconfig', []);
      if (result.exitCode == 0) {
        RegExp ipRegex = RegExp(r'(192\.168\.\d+\.\d+)');
        final res = ipRegex.allMatches(result.stdout.toString()).map((e) {
          return e.group(0);
        }).toList();
        list = res.cast<String>();
      }
    } catch (e) {
      debugPrint("getWifiAddressList: ${e.toString()}");
    }
    return list;
  }
}
