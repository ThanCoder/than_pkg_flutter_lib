import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class AppUtil {
  static final AppUtil instance = AppUtil._();
  AppUtil._();
  factory AppUtil() => instance;

  Future<void> clearImageCache() async {
  PaintingBinding.instance.imageCache.clear();
  PaintingBinding.instance.imageCache.clearLiveImages();
  await Future.delayed(const Duration(milliseconds: 500));
}

void copyText(String text) {
  try {
    Clipboard.setData(ClipboardData(text: text));
  } catch (e) {
    debugPrint('copyText: ${e.toString()}');
  }
}

Future<String> pasteText() async {
  String res = '';
  ClipboardData? data = await Clipboard.getData('text/plain');
  if (data != null) {
    res = data.text ?? '';
  }
  return res;
}

  String getParseMinutes(int minutes) {
    String res = '';
    try {
      final dur = Duration(minutes: minutes);
      res = '${_getTwoZero(dur.inHours)}:${_getTwoZero(dur.inMinutes)}';
    } catch (e) {
      debugPrint('getParseMinutes: ${e.toString()}');
    }
    return res;
  }

  static String _getTwoZero(int num) {
    return num < 10 ? '0$num' : '$num';
  }
}
