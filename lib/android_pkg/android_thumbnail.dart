import 'package:flutter/services.dart';

class AndroidThumbnail {
  static final AndroidThumbnail thumbnail = AndroidThumbnail._();
  AndroidThumbnail._();
  factory AndroidThumbnail() => thumbnail;

  final _channel = const MethodChannel('than_pkg');
  final _namePdf = 'pdfUtil';
  final _nameVideo = 'videoUtil';

  Future<void> genVideoThumbnailList({
    required String outDirPath,
    required List<String> videoPathList,
    int iconSize = 300,
  }) async {
    await _channel.invokeMethod('$_nameVideo/genVideoThumbnailList', {
      'path_list': videoPathList,
      'out_dir_path': outDirPath,
      'icon_size': iconSize,
    });
  }

  Future<String?> genVideoThumbnail({
    required String outPath,
    required String videoPath,
  }) async {
    return await _channel
        .invokeMethod<String>('$_nameVideo/genVideoThumbnail', {
      'video_path': videoPath,
      'out_path': outPath,
    });
  }

  //pdf
  Future<void> genPdfCoverList({
    required String outDirPath,
    required List<String> pdfPathList,
    int iconSize = 300,
  }) async {
    await _channel.invokeMethod('$_namePdf/genPdfCoverList', {
      'out_dir_path': outDirPath,
      'pdf_path_list': pdfPathList,
      'size': iconSize,
    });
  }

  Future<String> genPdfImage({
    required String pdfPath,
    required String outPath,
    int imageSize = -1, // -1 is pdf.width&& pdf.height
    int pageIndex = 0, //0 base
  }) async {
    return await _channel.invokeMethod<String>('$_namePdf/genPdfImage', {
          'pdf_path': pdfPath,
          'out_path': outPath,
          'page_index': pageIndex,
          'size': imageSize,
        }) ??
        '';
  }

  Future<int> getPdfPageCount({required String pdfPath}) async {
    return await _channel.invokeMethod<int>('$_namePdf/getPdfPageCount', {
          'pdf_path': pdfPath,
        }) ??
        0;
  }
}
