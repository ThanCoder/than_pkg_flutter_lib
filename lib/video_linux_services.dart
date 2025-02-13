import 'dart:io';
import 'dart:math';

/// Generate video thumbnail at a random timestamp
Future<void> generateVideoRandomThumbnail(
    String videoPath, String outputPath) async {
  int? duration = await getVideoDuration(videoPath);
  if (duration == null || duration < 1) {
    print("Invalid duration. Cannot generate thumbnail.");
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
      print("Thumbnail generated at $timeFormat: $outputPath");
    } else {
      print("Error: ${result.stderr}");
    }
  } catch (e) {
    print("Exception: $e");
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
    print("Error getting duration: $e");
  }
  return null;
}
