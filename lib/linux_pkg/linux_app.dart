import 'dart:io';

class LinuxApp {
  static final LinuxApp app = LinuxApp._();
  LinuxApp._();
  factory LinuxApp() => app;

  Future<void> launch(String source) async {
    await Process.run('xdg-open', [source]);
  }
}
