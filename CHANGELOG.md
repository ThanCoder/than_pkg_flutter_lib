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