### launch
- `ThanPkg.linux.app.launch`
- `ThanPkg.platform.launch`

- Ubuntu/Debian
```bash
sudo apt install xdg-utils
```
- Arch
```bash
sudo pacman -S xdg-utils
```
- Fedora
```bash
sudo dnf install xdg-utils
```

### Android ScreenOrientation

```Dart
final type = await ThanPkg.android.app.getOrientation();
if (type == null) return;
if (type == ScreenOrientationTypes.Portrait) {
await ThanPkg.android.app
    .requestOrientation(type: ScreenOrientationTypes.Landscape);
await ThanPkg.android.app.showFullScreen();
} else {
ThanPkg.android.app
    .requestOrientation(type: ScreenOrientationTypes.Portrait);
await ThanPkg.android.app.hideFullScreen();
}
```

### Added

```xml
<application>
<provider
    android:name="than.plugin.than_pkg.TContentProvider"
    android:authorities="${applicationId}.provider"
    android:exported="false"
    android:grantUriPermissions="true" />

</application>
```

- for above android xml code

```Dart
Future<void> openPdfWithIntent({required String path})
Future<void> openVideoWithIntent({required String path})
Future<void> installApk({required String path})
```

### Android Thumbnail

- ThanPkg.android.thumbnail

```Dart
Future<void> genVideoThumbnailList
Future<String?> genVideoThumbnail
Future<void> genPdfCoverList
Future<String> genPdfImage
Future<int> getPdfPageCount
```

### Android Camera

```Dart
final filePath = await ThanPkg.android.camera.openCamera();
```

### Android Permission (All Version)

```Dart
if (!await ThanPkg.android.permission.isStoragePermissionGranted()) {
    await ThanPkg.android.permission.requestStoragePermission();
    return;
}
if (!await ThanPkg.android.permission.isCameraPermission()) {
    await ThanPkg.android.permission.requestCameraPermission();
    return;
}
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

# needed lib from linux

```bash
sudo apt install net-tools  // wifi
sudo apt install poppler-utils //pdf thumbnail
sudo apt install ffmpeg //video thumbnail
```

# Android && linux

```Dart
//old method
await ThanPkg.windowManagerensureInitialized();
await ThanPkg.platform.toggleFullScreen(isFullScreen: true);
await ThanPkg.platform.getAppRootPath();
await ThanPkg.platform.getAppExternalPath();
await ThanPkg.platform.getWifiAddressList();
await ThanPkg.platform.genPdfCover(outDirPath: '', pdfPathList: []);
await ThanPkg.platform.genVideoCover(outDirPath: '', videoPathList: []);
```

# Android Permission

```Dart
//android any version can handle
await ThanPkg.platform.isStoragePermissionGranted();
await ThanPkg.platform.requestStoragePermission();
await ThanPkg.platform.checkAndRequestPackageInstallPermission();
```

# Android only

```Dart
//old method
await ThanPkg.platform.getAppFilePath();
await ThanPkg.platform.isAppSystemThemeDarkMode();
await ThanPkg.platform.isAppInternetConnected();
await ThanPkg.platform.getAppBatteryLevel();
await ThanPkg.platform.getLastKnownLocation();
await ThanPkg.platform.getInstalledApps();
await ThanPkg.platform.checkScreenOrientation();
await ThanPkg.platform.requestScreenOrientation(type: ScreenOrientationTypes.Portrait);
//android device info <Map> type
await ThanPkg.platform.getAndroidDeviceInfo()
await ThanPkg.platform.openUrl(url: '');
await ThanPkg.platform.getPlatformVersion();
await ThanPkg.platform.getDeviceId();
await ThanPkg.platform.toggleKeepScreen(isKeep: false);
await ThanPkg.platform.toggleFullScreen(isFullScreen: !isFullScreen);
await ThanPkg.platform.getLocalIpAddress();
await ThanPkg.platform.getWifiAddress();
```

# android AndroidManifest

```xml
<uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

```
