import 'dart:io';
import 'dart:math';

import 'package:flutter/foundation.dart';

class LinuxThumbnail {
  static final LinuxThumbnail thumbnail = LinuxThumbnail._();
  LinuxThumbnail._();
  factory LinuxThumbnail() => thumbnail;

  Future<void> genVideoCoverList({
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

  Future<void> genPdfCoverList({
    required String outDirPath,
    required List<String> pdfPathList,
    int iconSize = 300,
  }) async {
    try {
      for (final pdfPath in pdfPathList) {
        final name = pdfPath.split('/').last;
        final outputPath = '$outDirPath/${name.split('.').first}';
        final outImageFile = File(outputPath);
        final oldImagePath = '${outImageFile.path}.png';
        if (await File(oldImagePath).exists()) {
          continue;
        }
        await Process.run('pdftoppm',
            ['-png', '-f', '1', '-singlefile', pdfPath, outImageFile.path]);
      }
    } catch (e) {
      debugPrint("genPdfCover: ${e.toString()}");
    }
  }

  Future<void> generateVideoRandomThumbnail(
      String videoPath, String outputPath) async {
    int? duration = await getVideoDuration(videoPath);
    if (duration == null || duration < 1) {
      debugPrint("Invalid duration. Cannot generate thumbnail.");
      return;
    }

    // Generate a random time within video duration
    int randomTime = Random().nextInt(duration);
    String timeFormat =
        "00:${(randomTime ~/ 60).toString().padLeft(2, '0')}:${(randomTime % 60).toString().padLeft(2, '0')}";

    try {
      ProcessResult result = await Process.run('ffmpeg',
          ['-i', videoPath, '-ss', timeFormat, '-vframes', '1', outputPath]);

      if (result.exitCode == 0) {
        debugPrint("Thumbnail generated at $timeFormat: $outputPath");
      } else {
        debugPrint("Error: ${result.stderr}");
      }
    } catch (e) {
      debugPrint("Exception: $e");
    }
  }

  /// Get video duration in seconds
  Future<int?> getVideoDuration(String videoPath) async {
    try {
      ProcessResult result = await Process.run('ffmpeg', [
        '-i',
        videoPath,
        '-hide_banner',
        '-print_format',
        'json',
        '-show_entries',
        'format=duration',
        '-v',
        'quiet'
      ]);

      if (result.exitCode == 0) {
        RegExp regex = RegExp(r'"duration"\s*:\s*"(\d+\.\d+)"');
        Match? match = regex.firstMatch(result.stdout);
        if (match != null) {
          return double.parse(match.group(1)!).toInt();
        }
      }
    } catch (e) {
      debugPrint("Error getting duration: $e");
    }
    return null;
  }
}
