## 0.0.3

* TODO: Describe initial release.
# android only support

    အသုံးပြုနိုင်တဲ့ Methods တွေပါ

    await ThanPkg.platform.openUrl(url: 'https://www.youtube.com/watch?v=MIWte3C6vYw');
    await ThanPkg.platform.getPlatformVersion();
    await ThanPkg.platform.getDeviceId();
    await ThanPkg.platform.toggleKeepScreen(isKeep: false);
    await ThanPkg.platform.toggleFullScreen(isFullScreen: !isFullScreen);
    await ThanPkg.platform.isStoragePermissionGranted();
    await ThanPkg.platform.requestStoragePermission();
    await ThanPkg.platform.checkAndRequestPackageInstallPermission();
    await ThanPkg.platform.getLocalIpAddress();
    await ThanPkg.platform.getWifiAddress();
    await ThanPkg.platform.getWifiAddressList();
    await ThanPkg.platform.genPdfCover(outDirPath: outDirPath, pdfPathList: pdfPathList);
