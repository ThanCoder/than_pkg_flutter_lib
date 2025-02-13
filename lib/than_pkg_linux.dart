import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:than_pkg/than_pkg.dart';

class ThanPkgLinux extends ThanPkg {
  @override
  Future<String?> getAppRootPath() async {
    return Directory.current.path;
  }

  @override
  Future<String?> getAppExternalPath() async {
    return Platform.environment['HOME'] ?? '';
  }

  @override
  Future<void> genVideoCover({
    required Comparable<String> outDirPath,
    required List<String> videoPathList,
    int iconSize = 300,
  }) async {
    try {
      for (final videoPath in videoPathList) {
        final name = videoPath.split('/').last;
        final outputPath = '$outDirPath/${name.split('.').first}.png';
        final outImageFile = File(outputPath);
        if (await outImageFile.exists()) {
          continue;
        }
        await Process.run('ffmpeg', [
          '-i', videoPath,
          '-ss', '00:00:05', // Capture at 5 seconds
          '-vframes', '1',
          outImageFile.path
        ]);
      }
    } catch (e) {
      debugPrint("genVideoCover: ${e.toString()}");
    }
  }

  @override
  Future<void> genPdfCover({
    required String outDirPath,
    required List<String> pdfPathList,
    int iconSize = 300,
  }) async {
    try {
      for (final pdfPath in pdfPathList) {
        final name = pdfPath.split('/').last;
        final outputPath = '$outDirPath/${name.split('.').first}';
        final outImageFile = File(outputPath);
        if (await outImageFile.exists()) {
          continue;
        }
        await Process.run('pdftoppm',
            ['-png', '-f', '1', '-singlefile', pdfPath, outImageFile.path]);
      }
    } catch (e) {
      debugPrint("genPdfCover: ${e.toString()}");
    }
  }

  @override
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
