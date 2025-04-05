## 1.6.6

## Added
- `ThanPkg.platform.genVideoThumbnail`
- `ThanPkg.platform.genPdfThumbnail`
- `ThanPkg.android.thumbnail.genVideoThumbnail2`
- `ThanPkg.linux.thumbnail.genPdfThumbnail`

## 1.6.5

## Added
- `ThanPkg.linux.app.*`
- `ThanPkg.linux.app.launch`
- `ThanPkg.platform.launch`

## 1.6.4

## Added
- `ThanPkg.android.app.getOrientation` old method -> `ThanPkg.android.app.checkOrientation`

## Fixed
- `ThanPkg.android.app.requestOrientation` fixed

## 1.6.3

## Fixed
- `ThanPkg.android.wifi.*` fixed

## 1.6.2

### Added

## Linux
- Added `ThanPkg.linux`
```Dart
ThanPkg.linux.thumbnail.*;
ThanPkg.linux.wifi.*
```

## Android
- ThanPkg.android.app

```Dart
//android xml permission needed -> loop up README.md 
Future<void> openPdfWithIntent({required String path})
Future<void> openVideoWithIntent({required String path})
Future<void> installApk({required String path})

Future<Map<String, dynamic>> getDeviceInfo()
Future<void> showToast(String message)
Future<int> getSdkInt()
```

### Fixed

- ThanPkg.platform

```Dart
Future<void> genPdfCover({required String outDirPath,required List<String> pdfPathList,int iconSize = 300})
```

## 1.6.1

### Added

- ThanPkg.android.thumbnail

```Dart
Future<void> genVideoThumbnailList({
    required String outDirPath,
    required List<String> videoPathList,
    int iconSize = 300,
  })
Future<String?> genVideoThumbnail({
    required String outPath,
    required String videoPath,
  })
Future<void> genPdfCoverList({
    required String outDirPath,
    required List<String> pdfPathList,
    int iconSize = 300,
  })
Future<String> genPdfImage({
    required String pdfPath,
    required String outPath,
    int imageSize = -1, // -1 is pdf.width&& pdf.height
    int pageIndex = 0, //0 base
  })
Future<int> getPdfPageCount({required String pdfPath})
```

- ThanPkg.android.permission

```Dart
Future<void> checkCanRequestPackageInstallsPermission
Future<bool> isPackageInstallPermission()
Future<bool> isStoragePermissionGranted()
Future<bool> isCameraPermission()
Future<bool> isLocationPermission()
Future<void> requestStoragePermission()
Future<void> requestPackageInstallPermission()
Future<void> requestCameraPermission()
Future<void> requestLocationPermission()
```

- ThanPkg.android.camera

```Dart
Future<String> openCamera()
```

- ThanPkg.android.wifi

```Dart
Future<String> getWifiSSID()
Future<void> getLocalIpAddress()
Future<void> getWifiAddress()
Future<List<String>> getWifiAddressList()
```

- ThanPkg.android.app

```Dart
Future<void> openUrl({required String url})
Future<void> hideFullScreen()
Future<void> showFullScreen()
Future<List<Map<String, dynamic>>> getInstalledAppsList()
Future<int> getBatteryLevel()
Future<bool> isInternetConnected()
Future<bool> isDarkModeEnabled()
Future<String> getFilesDir()
Future<String> getExternalFilesDir()
Future<String> getAppExternalPath()
Future<void> requestOrientation({required ScreenOrientationTypes type,bool isReverse = false,})
Future<bool> checkOrientation()
Future<String> getPlatformVersion()
Future<void> toggleKeepScreenOn({required bool isKeep})
Future<String> getDeviceId()
```

## 1.6.0

## Added

- android native method
- `ThanPkg.android.*`

## Fixed

- `ThanPkg.platform.genVideoCover` thread error

## 1.5.4

## Added

- `Future<PackageInfo> getPackageInfo()` add package_info_plus package ထည့်ထား

## Fixed

- `Future<void> genVideoCover({
  required String outDirPath,
  required List<String> videoPathList,
  int iconSize = 300,
})` fixed error - error ပြန်ပြင်ပြီးပါပြီ။

## 1.5.3

## Fixed

- `genPdfCover()` linux platfrom -> override gen png
- `windowManagerensureInitialized()` methodUnimplementedError() -> fixed
- `isStoragePermissionGranted()` methodUnimplementedError() -> fixed

## 1.5.1

### Added

- added for linux,
- ensure do it. `windowManagerensureInitialized();`
- `toggleFullScreen(isFullScreen: true);`
